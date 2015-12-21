package rmk35.partIIProject.backend;

import java.util.Map;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class JavaCallStatement extends Statement
{ NativeFieldStatement field;
  Method method;

  public JavaCallStatement(NativeFieldStatement field, String methodName, int parameterCount) throws NoSuchMethodException
  { this.field = field;
    for (Method m : field.field.getType().getMethods())
    { if (m.getName() == methodName && m.getParameterTypes().length == parameterCount)
      { boolean acc = true;
        for (Class t : m.getParameterTypes())
        { if (t != Object.class) acc = false;
        }
        if (acc) method = m;
      }
    }
    if (method == null) throw new NoSuchMethodException();
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                                      Map<IdentifierValue, Macro> macros,
                                      OutputClass output)
  { field.generateOutput(definitions, macros, output);
    output.addToPrimaryMethod((method.getDeclaringClass().isInterface())? "  invokeinterface " : "  invokevirtual ");
    output.addToPrimaryMethod(method.getDeclaringClass().getName().replaceAll("\\.", "/") + "/" + method.getName() + "(");
    for (Class<?> argument : method.getParameterTypes())
    { output.addToPrimaryMethod(NativeFieldStatement.toBinaryName(argument.getName()));
    }
    output.addToPrimaryMethod(")" + NativeFieldStatement.toBinaryName(method.getReturnType().getName()));
    if (method.getDeclaringClass().isInterface())
    { output.addToPrimaryMethod(" " +  (method.getParameterTypes().length + 1)); // +1 because of 'this'
    }
    output.addToPrimaryMethod("\n");
    output.decrementStackCount(method.getParameterTypes().length + 1);
  }
}