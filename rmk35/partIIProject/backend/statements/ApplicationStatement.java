package rmk35.partIIProject.backend.statements;

import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

public class ApplicationStatement extends Statement
{ Statement operator;
  // FIXME: single operand for the moment
  Statement operand;

  public ApplicationStatement(Statement operator, Statement operand)
  { this.operator = operator;
    this.operand = operand;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { operator.generateOutput(definitions, macros, output);
    // FIXME: checkcast for LambdaValue
    operand.generateOutput(definitions, macros, output);
    // Invoke operator.run with argument of operand
    // FIXME: do we need this? output.addToPrimaryMethod("  astore_1\n");
    output.addToPrimaryMethod("  invokevirtual rmk35/partIIProject/backend/runtimeValues/LambdaValue/run(Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;)Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;\n");
    output.decrementStackCount(1);
  }
}
