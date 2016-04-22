package rmk35.partIIProject.runtime;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.MalformedParametersException;

public class ObjectValue extends LambdaValue
{ private Object innerObject;

  public ObjectValue(Object innerObject)
  { this.innerObject = innerObject;
  }

  public Object getValue()
  { return innerObject;
  }

  @Override
  public Object toJavaValue()
  { return innerObject;
  }

  @Override
  public String toString()
  { return innerObject.toString();
  }

  @Override
  public String writeString()
  { return "#<java object: " + toString() + ">";
  }

  public boolean equal(RuntimeValue other)
  { return this.equals(other); // Java Object equals
  }
  public boolean eqv(RuntimeValue other) { return this == other; }
  public boolean eq(RuntimeValue other) { return this == other; }

  public RuntimeValue run(RuntimeValue argument)
  { ConsValue first = (ConsValue) argument;
    String message = ((IdentifierValue) first.getCar()).getValue();
    Object[] arguments = ((List) first.getCdr().toJavaValue()).toArray();
    try
    { List<Method> methods = applicableMethods(message, arguments, innerObject.getClass().getMethods());
      if (methods.isEmpty())
      { throw new NoSuchMethodException("Method with name \"" + message + "\" that takes \"" + Arrays.toString(arguments) + "\".");
      }
      Method method = getMostSpecificMethod(methods);
      return ValueHelper.toSchemeValue(method.invoke(innerObject, ValueHelper.castEach(arguments, method.getParameterTypes())));
    } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e)
    { throw new RuntimeException(e);
    }
  }

  public static <T extends Executable> List<T> applicableMethods(String name, Object[] arguments, T[] methods)
  { List<T> returnValue = new ArrayList<>();
         // Try to find a matching method,
         // Class.getMethod does not take
         // casting into account hence this horribleness
    for (T method : methods)
    { Class<?>[] parameterTypes = method.getParameterTypes();
      if (! method.getName().equals(name)) continue;
      if (parameterTypes.length != arguments.length) continue;
      if (! objectsSubtypeClasses(arguments, parameterTypes)) continue;
      returnValue.add(method);
    }
    return returnValue;
  }

  static boolean objectsSubtypeClasses(Object[] a, Class<?>[] b)
  { if (a.length != b.length) throw new MalformedParametersException("Two arrays don't have the same length.");
    for (int i = 0; i < a.length; i++)
    { if (! objectSubtypesClass(a[i], b[i])) return false;
    }
    return true;
  }

  static boolean classesSubtypeClasses(Class<?>[] a, Class<?>[] b)
  { if (a.length != b.length) throw new MalformedParametersException("Two arrays don't have the same length.");
    for (int i = 0; i < a.length; i++)
    { if (! classSubtypesClass(a[i], b[i])) return false;
    }
    return true;
  }

  // a is subtype of b
  static boolean objectSubtypesClass(Object a, Class<?> b)
  { return (a.getClass().isArray() && ((Object[]) a).length == 0) ||
      classSubtypesClass(a.getClass(), b);
  }

  public final static Map<Class<?>, Class<?>> primitivesMap = new HashMap<Class<?>, Class<?>>();
  static {
    primitivesMap.put(boolean.class, Boolean.class);
    primitivesMap.put(byte.class, Byte.class);
    primitivesMap.put(short.class, Short.class);
    primitivesMap.put(char.class, Character.class);
    primitivesMap.put(int.class, Integer.class);
    primitivesMap.put(long.class, Long.class);
    primitivesMap.put(float.class, Float.class);
    primitivesMap.put(double.class, Double.class);
  }
  static Class<?> fromPrimitive(Class<?> c)
  { return c.isPrimitive() ? primitivesMap.get(c) : c;
  }
  // a is subtype of b
  static boolean classSubtypesClass(Class<?> a, Class<?> b)
  { return (a.isArray() && b.isArray() && classSubtypesClass(a.getComponentType(), b.getComponentType())) ||
      b.isAssignableFrom(a) || fromPrimitive(b).isAssignableFrom(fromPrimitive(a));
  }

  // Undefined when methods don't have a least element
  // which are neither sub are supertypes of each other
  public static <T extends Executable> T getMostSpecificMethod(List<T> methods)
  { T returnValue = null;
    for (T method : methods)
    { if (returnValue == null)
      { returnValue = method;
      } else if (classesSubtypeClasses(method.getParameterTypes(), returnValue.getParameterTypes()))
      { returnValue = method;
      }
    }
    return returnValue;
  }
}
