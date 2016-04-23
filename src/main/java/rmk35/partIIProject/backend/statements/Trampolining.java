package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.TrampolineValue;

import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.lambdaValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;

public class Trampolining implements TailCallSettings
{ public void generateContinuation(ByteCodeMethod method)
  { method.addInstruction(new StaticCallInstruction(runtimeValueType, TrampolineValue.class, "bounceHelper", runtimeValueType));
  }

  public void generateCallStart(ByteCodeMethod method)
  { method.addInstruction(new NewObjectInstruction(TrampolineValue.class));
    method.addInstruction(new DupInstruction());
  }

  public void generateCallEnd(ByteCodeMethod method)
  { method.addInstruction(new NonVirtualCallInstruction(voidType, TrampolineValue.class.getName().replace('.', '/') + "/<init>", lambdaValueType, runtimeValueType));
  }
}
