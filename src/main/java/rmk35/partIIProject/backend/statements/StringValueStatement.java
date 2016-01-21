package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.StringValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.StringConstantInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.VoidType;
import rmk35.partIIProject.backend.instructions.types.ObjectType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class StringValueStatement extends Statement
{ String string;

  public StringValueStatement(String string)
  { this.string = string;
  }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("StringValueStatement"));
    output.addToPrimaryMethod(new NewObjectInstruction(StringValue.class));
    output.addToPrimaryMethod(new DupInstruction());
    output.addToPrimaryMethod(new StringConstantInstruction(string));
    output.addToPrimaryMethod(new NonVirtualCallInstruction(new VoidType(), "rmk35/partIIProject/backend/runtimeValues/StringValue/<init>", new ObjectType(String.class)));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
