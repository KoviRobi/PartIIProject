package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;

public class SwapInstruction implements Instruction
{ // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod outputClass)
  {
  }

  public String byteCode()
  { return "  swap";
  }
}
