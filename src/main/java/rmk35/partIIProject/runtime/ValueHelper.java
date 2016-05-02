package rmk35.partIIProject.runtime;

import rmk35.partIIProject.runtime.numbers.NumberValue;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.lang.reflect.Method;
import java.lang.reflect.Array;

public class ValueHelper
{ public static RuntimeValue toSchemeValue(Object value)
  { if (value instanceof RuntimeValue)
    { return (RuntimeValue) value;
    } else if (value instanceof Boolean)
    { return new BooleanValue((Boolean) value);
    } else if (value instanceof Number)
    { return NumberValue.parse(value.toString());
    } else if (value instanceof String)
    { return new StringValue((String) value);
    } else if (value instanceof Character)
    { return new CharacterValue((Character) value);
    } else if (value instanceof List)
    { ListIterator<Object> iterator = ((List<Object>) value).listIterator(((List<Object>) value).size());
      RuntimeValue returnValue = new NullValue();
      while (iterator.hasPrevious())
      { RuntimeValue item = toSchemeValue(iterator.previous()); // ToDo circular data structures
        returnValue = new ConsValue(item, returnValue);
      }
      return returnValue;
    } else if (value instanceof Object[])
    { Object[] objectArray = (Object[]) value;
      RuntimeValue[] array = new RuntimeValue[objectArray.length];
      for (int i = 0; i < objectArray.length; i++)
      { array[i] = toSchemeValue(objectArray[i]);
      }
      Class<RuntimeValue> lowestType = (Class<RuntimeValue>) arrayLowestElementType(array);
      return new VectorValue
        ((lowestType == null) ? array : (RuntimeValue[]) castArray(array, lowestType));
    } else if (value instanceof Class)
    { return new ClassValue((Class) value);
    } else if (value == null)
    { return new UnspecifiedValue();
    } else
    { return new ObjectValue(value);
    }
  }

  static List<Class<?>> getClassHierarchy(Class<?> startClass)
  { List<Class<?>> returnValue = new ArrayList<>();
    do
    { returnValue.add(startClass);
      startClass = startClass.getSuperclass();
    } while (! Object.class.equals(startClass));
    return returnValue;
  }

  // Hierarchies as returned by the above method
  static Class<?> getMostSpecificCommonClass(List<Class<?>> hierarchy1, List<Class<?>> hierarchy2)
  { ListIterator<Class<?>> iterator1 = hierarchy1.listIterator(hierarchy1.size());
    ListIterator<Class<?>> iterator2 = hierarchy2.listIterator(hierarchy2.size());
    Class<?> returnValue = Object.class;
    while (iterator1.hasPrevious() && iterator2.hasPrevious())
    { Class class1 = iterator1.previous();
      Class class2 = iterator2.previous();
      if (class1.equals(class2))
      { returnValue = class1;
      } else
      { break;
      }
    }
    return returnValue;
  }

  public static Class<?> getMostSpecificCommonClass(Class<?> a, Class<?> b)
  { return getMostSpecificCommonClass(getClassHierarchy(a), getClassHierarchy(b));
  }

  public static Class<?> arrayLowestElementType(Object[] array)
  { Class<?> type = null;
    for (Object object : array)
    { if (type == null)
      { type = object.getClass();
      } else
      { type = getMostSpecificCommonClass(type, object.getClass());
      }
    }
    return type;
  }

  // Assuming object is not a primitive, or a primitive array
  // Returns null if cast cannot work
  public static Object cast(Object object, Class<?> type)
  { if (type.isArray() && object instanceof Object[])
    { Object[] originalArray = (Object[]) object;
      Object[] returnValue = (Object[]) Array.newInstance(type.getComponentType(), originalArray.length);
      for (int i = 0; i < originalArray.length; i++)
      { returnValue[i] = cast(originalArray[i], type.getComponentType());
        if (returnValue[i] == null) return null;
      }
      return returnValue;
    } else if (type.equals(boolean.class) && object instanceof Boolean)
    { return ((Boolean)  object).booleanValue();
    } else if (type.equals(byte.class) && object instanceof Byte)
    { return ((Byte)  object).byteValue();
    } else if (type.equals(short.class) && object instanceof Short)
    { return ((Short)  object).shortValue();
    } else if (type.equals(char.class) && object instanceof Character)
    { return ((Character)  object).charValue();
    } else if (type.equals(int.class) && object instanceof Integer)
    { return ((Integer)  object).intValue();
    } else if (type.equals(long.class) && object instanceof Long)
    { return ((Long)  object).longValue();
    } else if (type.equals(float.class) && object instanceof Float)
    { return ((Float)  object).floatValue();
    } else if (type.equals(double.class) && object instanceof Double)
    { return ((Double)  object).doubleValue();
    } else
    { return type.cast(object);
    }
  }

  public static Object castArray(Object[] objects, Class<?> componentType)
  { Object[] returnValue = (Object[]) Array.newInstance(componentType, objects.length);
    for (int i = 0; i < objects.length; i++)
    { returnValue[i] = cast(objects[i], componentType);
      if (returnValue[i] == null) return null;
    }
    return returnValue;
  }

  public static Object[] castEach(Object[] objects, Class<?>[] classes)
  { Object[] returnValue = new Object[classes.length];
    for (int i = 0; i < objects.length; i++)
    { returnValue[i] = ValueHelper.cast(objects[i], classes[i]);
    }
    return returnValue;
  }
}