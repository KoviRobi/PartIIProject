package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.runtime.RuntimeValue;
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

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("IfStatement"));
    predicate.generateOutput(mainClass, outputClass, method);
    // Top of stack is now predicate's value
    // XXX Speed: if we make booleans unique, we could use "if_acmpeq" to compare false
    (new BooleanValueStatement(false)).generateOutput(mainClass, outputClass, method);
    method.addInstruction(new InterfaceCallInstruction(/* static */ false, new BooleanType(), "rmk35/partIIProject/runtime/RuntimeValue/eq", runtimeValueType));

    // Stack now contains 1 if predicate is false, otherwise 0.
    String uniqueID = outputClass.uniqueID();
    String falseLabel = "FalseCase" + uniqueID;
    String endLabel = "IfEnd" + uniqueID;
    method.addInstruction(new IfNotEqualsInstruction(falseLabel)); // ifne branches if non 0, but 0 is boolean false (hence predicate true by above)
    trueCase.generateOutput(mainClass, outputClass, method);
    method.addInstruction(new GotoInstruction(endLabel));

    method.addInstruction(new LabelPseudoInstruction(falseLabel));
    falseCase.generateOutput(mainClass, outputClass, method);

    method.addInstruction(new LabelPseudoInstruction(endLabel));
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
