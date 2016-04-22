package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;

import java.util.Collection;
import java.util.TreeSet;

import lombok.Value;

@Value
public class RuntimeValueStatement extends Statement
{ RuntimeValue value;

  public RuntimeValueStatement(RuntimeValue value)
  { this.value = value;
  }

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("RuntimeValue: " + value));
    value.generateByteCode(mainClass, outputClass, method);
  }
}
