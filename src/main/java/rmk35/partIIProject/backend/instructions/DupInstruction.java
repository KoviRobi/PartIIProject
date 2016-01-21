package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.OutputClass;

public class DupInstruction implements Instruction
{ // Called when adding to primary method
  public void simulateLimits(OutputClass outputClass)
  { outputClass.incrementStackCount(1);
  }

  public String byteCode()
  { return "  dup";
  }
}
