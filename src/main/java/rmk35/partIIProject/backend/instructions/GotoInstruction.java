package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;

public class GotoInstruction implements Instruction
{ String label;

  public GotoInstruction(String label)
  { this.label = label;
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod outputClass)
  {
  }

  public String byteCode()
  { return "  goto " + label;
  }
}
