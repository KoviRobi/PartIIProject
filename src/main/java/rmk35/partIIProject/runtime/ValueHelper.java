package rmk35.partIIProject.runtime;

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
    } else if (value instanceof Integer)
    { return new NumberValue((Integer) value);
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
      if (lowestType == null)
      { return new VectorValue(array);
      } else
      { RuntimeValue[] returnValue = (RuntimeValue[]) Array.newInstance(lowestType, array.length);
        for (int i = 0; i < array.length; i++)
        { returnValue[i] =array[i];
        }
        return new VectorValue(returnValue);
      }
    } else if (value instanceof Class)
    { return new ClassValue((Class) value);
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

  public static Object[] downCastedArrayClone(Object[] array)
  { Class<?> lowestType = arrayLowestElementType(array);
     if (lowestType == null)
    { return new Object[0];
    } else
    { Object[] returnValue = (Object[]) Array.newInstance(lowestType, array.length);
      for (int i = 0; i < array.length; i++)
      { returnValue[i] = array[i];
      }
      return returnValue;
    }
  }
}