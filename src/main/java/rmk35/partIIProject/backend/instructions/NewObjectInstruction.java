package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;

public class NewObjectInstruction implements Instruction
{ String className;

  public NewObjectInstruction(Class<?> className)
  { this.className = className.getName().replace('.', '/');
  }

  /**
   * @deprecated Use the constructor taking Class, whenever possible
   */
  @Deprecated
  public NewObjectInstruction(String className)
  { this.className = className;
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  { method.incrementStackCount(1);
  }

  public String byteCode()
  { return "  new " + className;
  }
}
