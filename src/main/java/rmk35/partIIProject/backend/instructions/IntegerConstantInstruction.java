package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.OutputClass;

public class IntegerConstantInstruction implements Instruction
{ long number;

  public IntegerConstantInstruction(long number)
  { this.number = number;
  }

  // Called when adding to primary method
  public void simulateLimits(OutputClass outputClass)
  { outputClass.incrementStackCount(1);
  }

  public String byteCode()
  { if (number == -1)
    { return "  iconst_m1\n";
    } else if (0 <= number && number <= 5)
    { return "  iconst_" + number;
    } else
    { return "  ldc " + number;
    }
  }
}
