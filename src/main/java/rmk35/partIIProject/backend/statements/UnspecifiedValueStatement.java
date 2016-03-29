package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.UnspecifiedValue;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class UnspecifiedValueStatement extends Statement
{ public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("UndefinedValueStatement"));
    new UnspecifiedValue().generateByteCode(mainClass, outputClass, method);
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
