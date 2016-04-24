package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.CallValue;
import rmk35.partIIProject.runtime.Trampoline;

import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.lambdaValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;

public class Trampolining implements TailCallSettings
{ public void generateStartStart(ByteCodeMethod method)
  { method.addInstruction(new NewObjectInstruction(CallValue.class));
    method.addInstruction(new DupInstruction());
  }

  public void generateStartEnd(ByteCodeMethod method)
  { method.addInstruction(new NonVirtualCallInstruction(voidType, CallValue.class, "<init>", lambdaValueType, runtimeValueType));
    method.addInstruction(new StaticCallInstruction(runtimeValueType, Trampoline.class, "bounce", runtimeValueType));
  }

  public void generateContinuation(ByteCodeMethod method)
  { method.addInstruction(new StaticCallInstruction(runtimeValueType, Trampoline.class, "bounce", runtimeValueType));
  }

  public void generateCallStart(ByteCodeMethod method)
  { method.addInstruction(new NewObjectInstruction(CallValue.class));
    method.addInstruction(new DupInstruction());
  }

  public void generateCallEnd(ByteCodeMethod method)
  { method.addInstruction(new NonVirtualCallInstruction(voidType, CallValue.class, "<init>", lambdaValueType, runtimeValueType));
  }
}
