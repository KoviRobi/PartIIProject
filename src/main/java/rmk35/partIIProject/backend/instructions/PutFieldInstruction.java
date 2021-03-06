package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.types.JVMType;

public class PutFieldInstruction implements Instruction
{ JVMType type;
  String name;

  public PutFieldInstruction(JVMType type, String name)
  { this.type = type;
    this.name = name;
  }

  public PutFieldInstruction(JVMType type, Class<?> containingClass, String name)
  { this.type = type;
    this.name = containingClass.getName().replace('.', '/') + "/" + name;
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  { method.decrementStackCount(2); // Object (who's field we are setting) and value
  }

  public String byteCode()
  { return "  putfield " + name + " " + type;
  }
}
