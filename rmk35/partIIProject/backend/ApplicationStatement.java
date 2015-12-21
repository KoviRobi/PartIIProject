package rmk35.partIIProject.backend;

import java.util.Map;

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
    operand.generateOutput(definitions, macros, output);
    // Invoke operator.run with argument of operand
    output.addToPrimaryMethod("  invokevirtual rmk35/partIIProject/backend/LambdaValue/run(Lrmk35/partIIProject/backend/RuntimeValue;)Lrmk35/partIIProject/backend/RuntimeValue;\n");
  output.decrementStackCount(1);
  }
}