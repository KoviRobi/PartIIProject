package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.LambdaValue;

import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;

public class NonTailCalls implements TailCallSettings
{ public void generateContinuation(ByteCodeMethod method)
  {
  }

  public void generateCallStart(ByteCodeMethod method)
  {
  }

  public void generateCallEnd(ByteCodeMethod method)
  { method.addInstruction(new VirtualCallInstruction(runtimeValueType, LambdaValue.class.getName().replace('.', '/') + "/apply", runtimeValueType));
  }
}