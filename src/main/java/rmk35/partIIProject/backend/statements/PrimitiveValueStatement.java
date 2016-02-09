package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.PrimitiveValue;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.IntegerConstantInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.VoidType;
import rmk35.partIIProject.backend.instructions.types.IntegerType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.Value;

@Value
public class PrimitiveValueStatement extends Statement
{ PrimitiveValue value;

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { value.generateByteCode(mainClass, outputClass, method);
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
