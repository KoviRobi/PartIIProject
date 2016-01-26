package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.types.JVMType;

public class LocalLoadInstruction implements Instruction
{ JVMType type;
  int index;

  public LocalLoadInstruction(JVMType type, int index)
  { this.type = type;
    this.index = index;
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod outputClass)
  { outputClass.incrementStackCount(1);
  }

  public String byteCode()
  { if (0 <= index && index <= 3)
    { return "  " + type.prefix() + "load_" + index;
    } else if (0 <= index)
    { return "  " + type.prefix() + "load " + index;
    } else
    { throw new InternalCompilerException("Negative index for a local load!");
    }
  }
}
