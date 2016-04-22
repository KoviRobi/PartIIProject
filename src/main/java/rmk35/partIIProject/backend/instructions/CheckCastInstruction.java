package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;

public class CheckCastInstruction implements Instruction
{ String targetClass;

  public CheckCastInstruction(Class<?> targetClass)
  { this.targetClass = targetClass.getName().replace('.', '/');
  }

  public CheckCastInstruction(String targetClass)
  { this.targetClass = targetClass;
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod outputClass)
  {
  }

  public String byteCode()
  { return "  checkcast " + targetClass;
  }
}
