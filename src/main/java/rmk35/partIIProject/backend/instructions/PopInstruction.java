package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;

public class PopInstruction implements Instruction
{ // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  { method.decrementStackCount(1);
  }

  public String byteCode()
  { return "  pop";
  }
}
