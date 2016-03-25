package rmk35.partIIProject.runtime;

import java.util.List;
import java.util.Arrays;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

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

  public boolean equal(RuntimeValue other)
  { return this.equals(other); // Java Object equals
  }
  public boolean eqv(RuntimeValue other) { return this == other; }
  public boolean eq(RuntimeValue other) { return this == other; }

  public RuntimeValue apply(RuntimeValue argument)
  { ConsValue first = (ConsValue) argument;
    String message = ((IdentifierValue) first.getCar()).getValue();
    Object[] arguments =
      (first.getCdr() instanceof NullValue)
        ? new Object[0]
        : ((List) first.getCdr().toJavaValue()).toArray();
    try // Try to find a matching method,
         // Class.getMethod does not take
         // casting into account hence this horribleness
    { Method[] methods = innerObject.getClass().getMethods();
methodsLoop:
      for (Method method : methods)
      { Class<?>[] parameterTypes = method.getParameterTypes();
        if (!method.getName().equals(message)) continue;
        if (parameterTypes.length != arguments.length) continue;
        for (int i = 0; i < parameterTypes.length; i++)
        { if (!parameterTypes[i].isInstance(arguments[i])) continue methodsLoop;
        }
        Object[] castedArguments = new Object[arguments.length];
        for (int i = 0; i < parameterTypes.length; i++)
        { castedArguments[i] = parameterTypes[i].cast(arguments[i]);
        }
        return ValueHelper.toSchemeValue(method.invoke(innerObject, castedArguments));
      }
      throw new NoSuchMethodException("Method with name \"" + message + "\" that takes \"" + Arrays.toString(arguments) + "\".");
    } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e)
    { throw new RuntimeException(e);
    }
  }
}
