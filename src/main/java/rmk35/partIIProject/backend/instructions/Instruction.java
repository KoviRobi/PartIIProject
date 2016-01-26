package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;

public interface Instruction
{ // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method);
  public String byteCode();
}
