package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.OutputClass;

public class StringConstantInstruction implements Instruction
{ String string;

  public StringConstantInstruction(String string)
  { this.string = string;
  }

  // Called when adding to primary method
  public void simulateLimits(OutputClass outputClass)
  { outputClass.incrementStackCount(1);
  }

  public String byteCode()
  { return "  ldc \"" + string + "\"";
  }
}
