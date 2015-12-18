package rmk35.partIIProject.backend;

import java.util.Map;

public class IfStatement extends Statement
{ Statement predicate;
  Statement trueCase;
  Statement falseCase;

  public IfStatement(Statement predicate, Statement trueCase, Statement falseCase)
  { this.predicate = predicate;
    this.trueCase = trueCase;
    this.falseCase = falseCase;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass currentOutput)
  { predicate.generateOutput(definitions, macros, currentOutput);
    // Top of stack is now predicate's value
    currentOutput.addToPrimaryMethod("  new rmk35/partIIProject/backend/BooleanValue\n");
    currentOutput.addToPrimaryMethod("  dup\n"); // Because the <init> is void
    currentOutput.addToPrimaryMethod("  iconst_1\n"); // True
    currentOutput.addToPrimaryMethod("  invokenonvirtual rmk35/partIIProject/backend/BooleanValue/<init>(I)V\n");
    currentOutput.addToPrimaryMethod("  invokevirtual rmk35/partIIProject/backend/RuntimeValue/eq(Lrmk35/partIIProject/backend/RuntimeValue;)Z\n");

    String uniqueID = currentOutput.uniqueID();
    String falseLabel = "FalseCase" + uniqueID;
    String endLabel = "IfEnd" + uniqueID;
    currentOutput.addToPrimaryMethod("  ifeq " + falseLabel + "\n"); // ifeq has 'a eq b' as 'a-b=0', but 0 is boolean false
    trueCase.generateOutput(definitions, macros, currentOutput);
    currentOutput.addToPrimaryMethod("  goto " + endLabel + "\n");

    currentOutput.addToPrimaryMethod(falseLabel + ":\n");
    falseCase.generateOutput(definitions, macros, currentOutput);

    currentOutput.addToPrimaryMethod(endLabel + ":\n");
  }
}
