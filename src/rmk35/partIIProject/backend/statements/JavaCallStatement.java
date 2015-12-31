package rmk35.partIIProject.backend.statements;

import java.util.Map;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

public class JavaCallStatement extends Statement
{ NativeFieldStatement field;
  Statement[] parameters;
  Method method;

  public JavaCallStatement(NativeFieldStatement field, String methodName, Statement... parameters) throws NoSuchMethodException
  { this.field = field;
    this.parameters = parameters;
    for (Method m : field.field.getType().getMethods())
    { if (m.getName() == methodName && m.getParameterTypes().length == parameters.length)
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
  { output.addToPrimaryMethod("  ; JavaCallStatement\n");
    field.generateOutput(definitions, macros, output);
    for (Statement s : parameters)
    { s.generateOutput(definitions, macros, output);
    }

    // Create invokation
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
    // End create invokation

    output.decrementStackCount(method.getParameterTypes().length); // No +1 because of return value
    if (method.getReturnType() == void.class)
    { output.addToPrimaryMethod("  aconst_null\n"); // Which we always ensure
    }
    output.addToPrimaryMethod("\n");
  }
}