package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;

public class NewReferenceArrayInstruction implements Instruction
{ String underlyingType;

  public NewReferenceArrayInstruction(Class<?> underlyingType)
  { this.underlyingType = underlyingType.getName().replace('.', '/');
  }

  /**
   * @deprecated Use the constructor taking Class, whenever possible
   */
  @Deprecated
  public NewReferenceArrayInstruction(String underlyingType)
  { this.underlyingType = underlyingType;
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod outputClass)
  {
  }

  public String byteCode()
  { return "  anewarray " + underlyingType;
  }
}
