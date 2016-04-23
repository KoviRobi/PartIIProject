package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.types.JVMType;

public class PutStaticInstruction implements Instruction
{ JVMType type;
  String name;

  public PutStaticInstruction(JVMType type, String name)
  { this.type = type;
    this.name = name;
  }

  public PutStaticInstruction(JVMType type, Class<?> containingClass, String name)
  { this.type = type;
    this.name = containingClass.getName().replace('.', '/') + "/" + name;
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  { method.decrementStackCount(1);
  }

  public String byteCode()
  { return "  putstatic " + name + " " + type;
  }
}
