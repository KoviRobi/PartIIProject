package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;

public class ThrowInstruction implements Instruction
{ // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  { method.setStackCount(1);
  }

  public String byteCode()
  { return "  athrow";
  }
}
