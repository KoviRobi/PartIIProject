package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.instructions.types.JVMType;

public class GetFieldInstruction implements Instruction
{ JVMType type;
  String name;

  public GetFieldInstruction(JVMType type, String name)
  { this.type = type;
    this.name = name;
  }

  // Called when adding to primary method
  public void simulateLimits(OutputClass outputClass)
  {
  }

  public String byteCode()
  { return "  getfield " + name + " " + type;
  }
}
