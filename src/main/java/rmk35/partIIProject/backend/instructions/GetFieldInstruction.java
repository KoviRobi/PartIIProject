package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.types.JVMType;

public class GetFieldInstruction implements Instruction
{ JVMType type;
  String name;

  public GetFieldInstruction(JVMType type, String name)
  { this.type = type;
    this.name = name;
  }

  public GetFieldInstruction(JVMType type, Class<?> containingClass, String name)
  { this.type = type;
    this.name = containingClass.getName().replace('.', '/') + "/" + name;
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  {
  }

  public String byteCode()
  { return "  getfield " + name + " " + type;
  }
}
