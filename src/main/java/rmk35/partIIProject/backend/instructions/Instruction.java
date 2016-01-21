package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.OutputClass;

public interface Instruction
{ // Called when adding to primary method
  public void simulateLimits(OutputClass outputClass);
  public String byteCode();
}
