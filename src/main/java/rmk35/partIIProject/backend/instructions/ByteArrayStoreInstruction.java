package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;

public class ByteArrayStoreInstruction implements Instruction
{ // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  { method.decrementStackCount(3);
  }

  public String byteCode()
  { return "  bastore";
  }
}
