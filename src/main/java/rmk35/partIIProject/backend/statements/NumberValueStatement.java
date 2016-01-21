package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.NumberValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.IntegerConstantInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.VoidType;
import rmk35.partIIProject.backend.instructions.types.IntegerType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class NumberValueStatement extends Statement
{ int value;

  public NumberValueStatement(int value)
  { this.value = value;
  }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("NumberValueStatement"));
    output.addToPrimaryMethod(new NewObjectInstruction(NumberValue.class));
    output.addToPrimaryMethod(new DupInstruction());
    output.addToPrimaryMethod(new IntegerConstantInstruction(value));
    output.addToPrimaryMethod(new NonVirtualCallInstruction(new VoidType(), "rmk35/partIIProject/backend/runtimeValues/NumberValue/<init>", new IntegerType()));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
