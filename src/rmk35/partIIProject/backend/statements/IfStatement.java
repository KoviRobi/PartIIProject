package rmk35.partIIProject.backend.statements;

import java.util.Collection;
import java.util.TreeSet;
import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.BooleanValue;

import lombok.ToString;

@ToString
public class IfStatement extends Statement
{ Statement predicate;
  Statement trueCase;
  Statement falseCase;

  public IfStatement(Statement predicate, Statement trueCase, Statement falseCase)
  { this.predicate = predicate;
    this.trueCase = trueCase;
    this.falseCase = falseCase;
  }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod("  ; IfStatement\n");
    predicate.generateOutput(output);
    // Top of stack is now predicate's value
    // XXX Speed: if we make booleans unique, we could use "if_acmpeq" to compare false
    (new RuntimeValueStatement("0", BooleanValue.class, new String[] {"Z"})).generateOutput(output);
    output.addToPrimaryMethod("  invokeinterface rmk35/partIIProject/backend/runtimeValues/RuntimeValue/eq(Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;)Z 2\n");
    output.decrementStackCount(1); // Values compared for equality popped, result pushed

    // Stack now contains 1 if predicate is false, otherwise 0.
    String uniqueID = output.uniqueID();
    String falseLabel = "FalseCase" + uniqueID;
    String endLabel = "IfEnd" + uniqueID;
    output.addToPrimaryMethod("  ifne " + falseLabel + "\n"); // ifne branches if 0, but 0 is boolean false
    output.decrementStackCount(1); // Boolean popped
    trueCase.generateOutput(output);
    output.addToPrimaryMethod("  goto " + endLabel + "\n");

    output.addToPrimaryMethod(falseLabel + ":\n");
    falseCase.generateOutput(output);

    output.addToPrimaryMethod(endLabel + ":\n");
    output.addToPrimaryMethod("\n");
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> returnValue = new TreeSet<>();
    returnValue.addAll(predicate.getFreeIdentifiers());
    returnValue.addAll(trueCase.getFreeIdentifiers());
    returnValue.addAll(falseCase.getFreeIdentifiers());
    return returnValue;
  }
}
