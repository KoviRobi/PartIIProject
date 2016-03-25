package rmk35.partIIProject.runtime;

import java.util.List;
import java.util.Arrays;
import java.lang.reflect.Method;
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
    String message = ((IdentifierValue) first.getCar()).getValue();
    Object[] arguments =
      (first.getCdr() instanceof NullValue)
        ? new Object[0]
        : ((List) first.getCdr().toJavaValue()).toArray();
    if (message.equals("new"))
    { try // Try to find a matching constructor,
           // Class.getConstructor does not take
           // casting into account hence this horribleness
      { Constructor[] constructors = innerClass.getConstructors();
constructorsLoop:
        for (Constructor constructor : constructors)
        { Class<?>[] parameterTypes = constructor.getParameterTypes();
          if (parameterTypes.length != arguments.length) continue;
          for (int i = 0; i < parameterTypes.length; i++)
          { if (!parameterTypes[i].isInstance(arguments[i])) continue constructorsLoop;
          }
          Object[] castedArguments = new Object[arguments.length];
          for (int i = 0; i < parameterTypes.length; i++)
          { castedArguments[i] = parameterTypes[i].cast(arguments[i]);
          }
          return ValueHelper.toSchemeValue(constructor.newInstance(castedArguments));
        }
        throw new NoSuchMethodException("Constructor not found that takes \"" + Arrays.toString(arguments) + "\".");
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
      { throw new RuntimeException(e);
      }
    } else // Try to find a static method on the Object
            // that this class represents, before
            // finding a method on the Class itself (super.apply(...) call)
    { try // Try to find a matching method,
           // Class.getMethod does not take
           // casting into account hence this horribleness
      { Method[] methods = innerClass.getMethods();
methodsLoop:
        for (Method method : methods)
        { Class<?>[] parameterTypes = method.getParameterTypes();
          if (!method.getName().equals(message)) continue;
          if (!method.toString().contains("static")) continue;
          if (parameterTypes.length != arguments.length) continue;
          for (int i = 0; i < parameterTypes.length; i++)
          { if (!parameterTypes[i].isInstance(arguments[i])) continue methodsLoop;
          }
          Object[] castedArguments = new Object[arguments.length];
          for (int i = 0; i < parameterTypes.length; i++)
          { castedArguments[i] = parameterTypes[i].cast(arguments[i]);
          }
          return ValueHelper.toSchemeValue(method.invoke(null, castedArguments));
        }
        return super.apply(argument);
      } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e)
      { throw new RuntimeException(e);
      }
    }
  }
}
