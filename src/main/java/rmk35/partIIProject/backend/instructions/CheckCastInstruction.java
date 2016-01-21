package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.OutputClass;

public class CheckCastInstruction implements Instruction
{ String targetClass;

  public CheckCastInstruction(Class<?> targetClass)
  { this.targetClass = targetClass.getName().replace('.', '/');
  }

  // Called when adding to primary method
  public void simulateLimits(OutputClass outputClass)
  {
  }

  public String byteCode()
  { return "  checkcast " + targetClass;
  }
}
