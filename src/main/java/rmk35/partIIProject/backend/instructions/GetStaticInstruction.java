package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.instructions.types.JVMType;

public class GetStaticInstruction implements Instruction
{ JVMType type;
  String name;

  public GetStaticInstruction(JVMType type, String name)
  { this.type = type;
    this.name = name;
  }

  // Called when adding to primary method
  public void simulateLimits(OutputClass outputClass)
  { outputClass.incrementStackCount(1);
  }

  public String byteCode()
  { return "  getstatic " + name + " " + type + "\n";
  }
}
