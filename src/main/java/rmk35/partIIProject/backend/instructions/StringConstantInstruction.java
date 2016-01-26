package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;

public class StringConstantInstruction implements Instruction
{ String string;

  public StringConstantInstruction(String string)
  { this.string = string;
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod outputClass)
  { outputClass.incrementStackCount(1);
  }

  public String byteCode()
  { return "  ldc \"" + string + "\"";
  }
}
