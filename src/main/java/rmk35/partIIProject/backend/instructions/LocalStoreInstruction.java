package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.instructions.types.JVMType;

public class LocalStoreInstruction implements Instruction
{ JVMType type;
  int index;

  public LocalStoreInstruction(JVMType type, int index)
  { this.type = type;
    this.index = index;
  }

  // Called when adding to primary method
  public void simulateLimits(OutputClass outputClass)
  { outputClass.decrementStackCount(1);
  }

  public String byteCode()
  { if (0 <= index && index <= 3)
    { return "  " + type.prefix() + "store_" + index;
    } else if (0 <= index)
    { return "  " + type.prefix() + "store " + index;
    } else
    { throw new InternalCompilerException("Negative index for a local load!");
    }
  }
}
