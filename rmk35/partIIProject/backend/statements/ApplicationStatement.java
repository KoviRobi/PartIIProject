package rmk35.partIIProject.backend.statements;

import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

public class ApplicationStatement extends Statement
{ Statement[] application;

  public ApplicationStatement(Statement... application)
  { this.application =  application;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { output.addToPrimaryMethod("  ; ApplicationStatement\n");
    application[0].generateOutput(definitions, macros, output);
    output.addToPrimaryMethod("  checkcast rmk35/partIIProject/backend/runtimeValues/LambdaValue\n");
    
    // Create new ArrayList for operands
    output.addToPrimaryMethod("  new java/util/ArrayList\n");
    output.addToPrimaryMethod("  dup\n");
    if (application.length - 1 < 6)
    { output.addToPrimaryMethod("  iconst_" + (application.length - 1) + "\n");
    } else
    { output.addToPrimaryMethod("  ldc " + (application.length - 1) + "\n");
    }
    output.incrementStackCount(3);
    output.addToPrimaryMethod("  invokenonvirtual java/util/ArrayList/<init>(I)V\n");
    output.decrementStackCount(2);

    // FIXME: multiple arguments
    for (int i = 1; i < application.length; i++)
    { output.addToPrimaryMethod("  dup\n"); // Loop invariant is the list on the top of the stack
      output.incrementStackCount(1);
      application[i].generateOutput(definitions, macros, output);
      output.addToPrimaryMethod("  invokeinterface java/util/List/add(Ljava/lang/Object;)Z 2\n");
      output.addToPrimaryMethod("  pop\n");
      output.decrementStackCount(2);
    }

    // Invoke operator.run with argument of operand
    output.addToPrimaryMethod("  invokevirtual rmk35/partIIProject/backend/runtimeValues/LambdaValue/run(Ljava/util/ArrayList;)Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;\n");
    output.decrementStackCount(1); // Operator and operand popped, result pushed
    output.addToPrimaryMethod("\n");
  }
}