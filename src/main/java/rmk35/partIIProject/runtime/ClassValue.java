package rmk35.partIIProject.runtime;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ClassValue extends ObjectValue
{ Class<?> innerClass;
  public ClassValue(Class<?> innerClass)
  { super(innerClass);
    this.innerClass = innerClass;
  }

  public Class<?> toJavaValue()
  { return innerClass;
  }

  @Override
  public RuntimeValue apply(RuntimeValue argument)
  { ConsValue first = (ConsValue) argument;
    String message = ((IdentifierValue) first.getCar()).getValue();
    Object[] arguments = ((List) first.getCdr().toJavaValue()).toArray();
    if (message.equals("new"))
    { try // Try to find a matching constructor,
           // Class.getConstructor does not take
           // casting into account hence this horribleness
      { List<Constructor> applicableConstructors = applicableMethods(innerClass.getName(), arguments, innerClass.getConstructors());
        if (applicableConstructors.isEmpty())
        { throw new NoSuchMethodException("Constructor not found that takes \"" + Arrays.toString(arguments) + "\".");
        }
        Constructor constructor = getMostSpecificMethod(applicableConstructors);
        return ValueHelper.toSchemeValue(constructor.newInstance(ValueHelper.castEach(arguments, constructor.getParameterTypes())));
      } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e)
      { throw new RuntimeException(e);
      }
    } else // Try to find a static method on the Object
            // that this class represents, before
            // finding a method on the Class itself (super.apply(...) call)
    { try
      { List<Method> staticMethods = new ArrayList<>();
        for (Method method : innerClass.getMethods())
        { if (Modifier.isStatic(method.getModifiers())) staticMethods.add(method);
        }
        List<Method> methods = applicableMethods(message, arguments, staticMethods.toArray(new Method[0]));
        if (methods.isEmpty()) return super.apply(argument);
        Method method = getMostSpecificMethod(methods);
        return ValueHelper.toSchemeValue(method.invoke(null, ValueHelper.castEach(arguments, method.getParameterTypes())));
      } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e)
      { throw new RuntimeException(e);
      }
    }
  }
}
