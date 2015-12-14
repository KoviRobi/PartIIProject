package rmk35.partIIProject.backend;

import java.util.Map;

public class IfStatement extends Statement
{ Statement predicate;
  Statement trueCase;
  Statement falseCase;
  public void generateOutput(Map<Identifier, Definition> definitions,
                             Map<Identifier, Macro> macros,
                             OutputClass currentOutput)
  { predicate.generateOutput(definitions, macros, currentOutput);
    // Top of stack is now predicate's value
    currentOutput.addToMainMethod("  new rmk35/partIIProject/backend/BooleanValue\n");
    currentOutput.addToMainMethod("  dup\n"); // Because the <init> is void
    currentOutput.addToMainMethod("  iconst_1\n"); // True
    currentOutput.addToMainMethod("  invokenonvirtual rmk35/partIIProject/backend/BooleanValue/<init>(I)V\n");
    currentOutput.addToMainMethod("  invokevirtual rmk35/partIIProject/backend/RuntimeValue/eq(Lrmk35/partIIProject/backend/RuntimeValue;)Z\n");

    int uniqueNumber = currentOutput.uniqueNumber();
    String falseLabel = "FalseCase" + uniqueNumber;
    String endLabel = "IfEnd" + uniqueNumber;
    currentOutput.addToMainMethod("  ifeq " + falseLabel + "\n"); // ifeq has 'a eq b' as 'a-b=0', but 0 is boolean false
    trueCase.generateOutput(definitions, macros, currentOutput);
    currentOutput.addToMainMethod("  goto " + endLabel + "\n");

    currentOutput.addToMainMethod(falseLabel + "\n");
    falseCase.generateOutput(definitions, macros, currentOutput);

    currentOutput.addToMainMethod(endLabel + "\n");
  }
}
