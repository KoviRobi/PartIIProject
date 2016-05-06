package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.CallValue;
import rmk35.partIIProject.runtime.CallStack;
import rmk35.partIIProject.runtime.LambdaValue;

import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.DupX2Instruction;
import rmk35.partIIProject.backend.instructions.PopInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.SwapInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.ReturnInstruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.callValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.callStackType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.lambdaValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;

public class SchemeCallStack implements TailCallSettings
{ public void generateStartStart(ByteCodeMethod method)
  {method.addInstruction(new StaticCallInstruction(callStackType, CallStack.class, "getCurrentCallStack"));
  }

  public void generateStartEnd(ByteCodeMethod method)
  { method.addInstruction(new VirtualCallInstruction(runtimeValueType, CallStack.class, "start", lambdaValueType, runtimeValueType));
  }

  public void generateContinuation(ByteCodeMethod method)
  { // Add stack frame and return value
    method.addJump();
  }

  public void generateCallStart(ByteCodeMethod method)
  {
  }

  public void generateCallEnd(ByteCodeMethod method)
  { method.addInstruction(new StaticCallInstruction(callValueType, CallValue.class, "create", runtimeValueType, runtimeValueType));
  }

  public void postJumpCleanUp(ByteCodeMethod method)
  { method.setProgrammeCounter();
  }
}
