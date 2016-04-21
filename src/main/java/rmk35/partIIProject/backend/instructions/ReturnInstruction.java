package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.types.JVMType;

public class ReturnInstruction implements Instruction
{ JVMType type;

  public ReturnInstruction(JVMType type)
  { this.type = type;
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  { method.decrementStackCount(type.stackCount());
  }

  public String byteCode()
  { return type.stackCount()==0
      ? "  return"
      : "  " + type.prefix() + "return";
  }
}
