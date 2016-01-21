package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.BooleanValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.IntegerConstantInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.VoidType;
import rmk35.partIIProject.backend.instructions.types.BooleanType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class BooleanValueStatement extends Statement
{ boolean value;

  public BooleanValueStatement(boolean value)
  { this.value = value;
  }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("BooleanValueStatement"));
    output.addToPrimaryMethod(new NewObjectInstruction(BooleanValue.class));
    output.addToPrimaryMethod(new DupInstruction());
    output.addToPrimaryMethod(new IntegerConstantInstruction(value? 1 : 0));
    output.addToPrimaryMethod(new NonVirtualCallInstruction(new VoidType(), "rmk35/partIIProject/backend/runtimeValues/BooleanValue/<init>", new BooleanType()));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
