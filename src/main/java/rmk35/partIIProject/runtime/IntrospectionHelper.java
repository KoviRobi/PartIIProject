package rmk35.partIIProject.runtime;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

public class IntrospectionHelper
{ public static Object getField(Object object, String fieldName)
  { try
    { return object.getClass().getField(fieldName).get(object);
    } catch (NoSuchFieldException | IllegalAccessException exception)
    { throw new RuntimeException(exception);
    }
  }

  public static Object getStaticField(String className, String fieldName)
  { try
    { return Class.forName(className).getField(fieldName).get(Class.forName(className));
    } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException exception)
    { throw new RuntimeException("Can't get static field", exception);
    }
  }

  public static Method getMethod(Object object, String methodName, String... argumentClassNames)
  { try
    { Class[] argumentTypes = new Class[argumentClassNames.length];
       for (int i = 0; i < argumentTypes.length; i++)
      { argumentTypes[i] = Class.forName(argumentClassNames[i]);
      }
      return object.getClass().getDeclaredMethod(methodName, argumentTypes);
    } catch (NoSuchMethodException | ClassNotFoundException exception)
    { throw new RuntimeException("Can't get method", exception);
    }
  }
}
