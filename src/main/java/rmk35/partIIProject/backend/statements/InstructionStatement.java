package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.Instruction;

import java.util.Collection;
import java.util.TreeSet;

import lombok.Value;

@Value
public class InstructionStatement extends Statement
{ Instruction instruction;

  public InstructionStatement(Instruction instruction)
  { this.instruction = instruction;
  }

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(instruction);
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
