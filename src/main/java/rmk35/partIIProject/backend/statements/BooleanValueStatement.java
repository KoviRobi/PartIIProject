package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.runtime.BooleanValue;
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

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("BooleanValueStatement"));
    method.addInstruction(new NewObjectInstruction(BooleanValue.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new IntegerConstantInstruction(value? 1 : 0));
    method.addInstruction(new NonVirtualCallInstruction(new VoidType(), "rmk35/partIIProject/runtime/BooleanValue/<init>", new BooleanType()));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
