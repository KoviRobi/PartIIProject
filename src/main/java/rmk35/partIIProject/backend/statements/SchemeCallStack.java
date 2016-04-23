package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.CallValue;
import rmk35.partIIProject.runtime.CallStack;

import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.lambdaValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;

public class SchemeCallStack implements TailCallSettings
{ public void generateStart(ByteCodeMethod method)
  { // invoke CallStack.start
  }

  public void generateContinuation(ByteCodeMethod method)
  { // add jump
  }

  public void generateCallStart(ByteCodeMethod method)
  { method.addInstruction(new NewObjectInstruction(CallValue.class));
    method.addInstruction(new DupInstruction());
  }

  public void generateCallEnd(ByteCodeMethod method)
  { method.addInstruction(new NonVirtualCallInstruction(voidType, CallValue.class, "<init>", lambdaValueType, runtimeValueType));
  }
}
