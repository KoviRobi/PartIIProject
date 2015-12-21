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
                             OutputClass output)
  { predicate.generateOutput(definitions, macros, output);
    // Top of  is now predicate's value
    output.addToPrimaryMethod("  new rmk35/partIIProject/backend/BooleanValue\n");
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  dup\n"); // Because the <init> is void
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  iconst_1\n"); // True
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  invokenonvirtual rmk35/partIIProject/backend/BooleanValue/<init>(I)V\n");
    output.decrementStackCount(2);
    output.addToPrimaryMethod("  invokevirtual rmk35/partIIProject/backend/RuntimeValue/eq(Lrmk35/partIIProject/backend/RuntimeValue;)Z\n");
    output.decrementStackCount(2);

    String uniqueID = output.uniqueID();
    String falseLabel = "FalseCase" + uniqueID;
    String endLabel = "IfEnd" + uniqueID;
    output.addToPrimaryMethod("  ifeq " + falseLabel + "\n"); // ifeq has 'a eq b' as 'a-b=0', but 0 is boolean false
    output.decrementStackCount(1);
    trueCase.generateOutput(definitions, macros, output);
    output.addToPrimaryMethod("  goto " + endLabel + "\n");

    output.addToPrimaryMethod(falseLabel + ":\n");
    falseCase.generateOutput(definitions, macros, output);

    output.addToPrimaryMethod(endLabel + ":\n");
  }
}
