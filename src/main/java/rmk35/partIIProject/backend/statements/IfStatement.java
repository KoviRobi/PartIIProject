package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.RuntimeValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.InterfaceCallInstruction;
import rmk35.partIIProject.backend.instructions.IfNotEqualsInstruction;
import rmk35.partIIProject.backend.instructions.GotoInstruction;
import rmk35.partIIProject.backend.instructions.LabelPseudoInstruction;
import rmk35.partIIProject.backend.instructions.types.ObjectType;
import rmk35.partIIProject.backend.instructions.types.BooleanType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class IfStatement extends Statement
{ Statement predicate;
  Statement trueCase;
  Statement falseCase;
  private static final ObjectType runtimeValueType = new ObjectType(RuntimeValue.class);

  public IfStatement(Statement predicate, Statement trueCase, Statement falseCase)
  { this.predicate = predicate;
    this.trueCase = trueCase;
    this.falseCase = falseCase;
  }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("IfStatement"));
    predicate.generateOutput(output);
    // Top of stack is now predicate's value
    // XXX Speed: if we make booleans unique, we could use "if_acmpeq" to compare false
    (new BooleanValueStatement(false)).generateOutput(output);
    output.addToPrimaryMethod(new InterfaceCallInstruction(/* static */ false, new BooleanType(), "rmk35/partIIProject/backend/runtimeValues/RuntimeValue/eq", runtimeValueType));

    // Stack now contains 1 if predicate is false, otherwise 0.
    String uniqueID = output.uniqueID();
    String falseLabel = "FalseCase" + uniqueID;
    String endLabel = "IfEnd" + uniqueID;
    output.addToPrimaryMethod(new IfNotEqualsInstruction(falseLabel)); // ifne branches if non 0, but 0 is boolean false (hence predicate true by above)
    trueCase.generateOutput(output);
    output.addToPrimaryMethod(new GotoInstruction(endLabel));

    output.addToPrimaryMethod(new LabelPseudoInstruction(falseLabel));
    falseCase.generateOutput(output);

    output.addToPrimaryMethod(new LabelPseudoInstruction(endLabel));
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
