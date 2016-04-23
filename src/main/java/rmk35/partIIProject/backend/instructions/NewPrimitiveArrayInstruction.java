package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.backend.ByteCodeMethod;

public class NewPrimitiveArrayInstruction implements Instruction
{ String underlyingType;

  public NewPrimitiveArrayInstruction(Class<?> underlyingType)
  { if (!underlyingType.isPrimitive()) throw new InternalCompilerException("Was expecting a primitive type");
    this.underlyingType = underlyingType.getName();
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  {
  }

  public String byteCode()
  { return "  newarray " + underlyingType;
  }
}
