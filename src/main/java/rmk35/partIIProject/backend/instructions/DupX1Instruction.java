package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;

/* value2, value1 → value1, value2, value1 */
public class DupX1Instruction implements Instruction
{ // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod outputClass)
  { outputClass.incrementStackCount(1);
  }

  public String byteCode()
  { return "  dup_x1";
  }
}
