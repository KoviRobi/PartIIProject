package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.runtime.StringValue;
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

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("StringValueStatement"));
    method.addInstruction(new NewObjectInstruction(StringValue.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new StringConstantInstruction(string));
    method.addInstruction(new NonVirtualCallInstruction(new VoidType(), "rmk35/partIIProject/runtime/StringValue/<init>", new ObjectType(String.class)));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
