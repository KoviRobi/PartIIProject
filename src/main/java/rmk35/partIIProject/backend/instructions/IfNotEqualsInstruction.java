package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.OutputClass;

public class IfNotEqualsInstruction implements Instruction
{ String label;

  public IfNotEqualsInstruction(String label)
  { this.label = label;
  }

  // Called when adding to primary method
  public void simulateLimits(OutputClass outputClass)
  { outputClass.decrementStackCount(1);
  }

  public String byteCode()
  { return "  ifne " + label;
  }
}
