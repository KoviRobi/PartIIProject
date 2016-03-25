package rmk35.partIIProject.runtime;

import java.util.List;
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

  public boolean equal(RuntimeValue other)
  { return this.equals(other); // Java Object equals
  }
  public boolean eqv(RuntimeValue other) { return this == other; }
  public boolean eq(RuntimeValue other) { return this == other; }

  public RuntimeValue apply(RuntimeValue argument)
  { ConsValue first = (ConsValue) argument;
    IdentifierValue message = (IdentifierValue) first.getCar();
    Object[] arguments;
    Class<?>[] argumentClasses;
    if (first.getCdr() instanceof NullValue)
    { arguments = null;
      argumentClasses = null;
    } else
    { arguments = ((List) first.getCdr().toJavaValue()).toArray();
      argumentClasses = new Class<?>[arguments.length];
      for (int i = 0; i < arguments.length; i++)
      { argumentClasses[i] = arguments[i].getClass();
      }
    }
    try
    { Method method = innerObject.getClass().getMethod(message.getValue(), argumentClasses);
      return ValueHelper.toSchemeValue(method.invoke(innerObject, arguments));
    } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e)
    { throw new RuntimeException(e);
    }
  }
}
