package rmk35.partIIProject.runtime;

import java.util.List;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ClassValue extends ObjectValue
{ Class<?> innerClass;
  public ClassValue(Class<?> innerClass)
  { super(innerClass);
    this.innerClass = innerClass;
  }
  @Override
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
    if (message.getValue().equals("new"))
    { try
      { Constructor<?> constructor = innerClass.getConstructor(argumentClasses);
        return ValueHelper.toSchemeValue(constructor.newInstance(arguments));
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
      { throw new RuntimeException(e);
      }
    } else
    { return super.apply(argument);
    }
  }
}
