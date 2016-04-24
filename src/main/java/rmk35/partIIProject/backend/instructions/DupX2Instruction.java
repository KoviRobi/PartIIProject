package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;

/* value3, value2, value1 → value1, value3, value2, value1 */
public class DupX2Instruction implements Instruction
{ // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  { method.incrementStackCount(1);
  }

  public String byteCode()
  { return "  dup_x2";
  }
}
