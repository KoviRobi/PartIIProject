package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.backend.ByteCodeMethod;

public interface TailCallSettings
{ public void generateStartStart(ByteCodeMethod method);
  public void generateStartEnd(ByteCodeMethod method);
  public void generateContinuation(ByteCodeMethod method);
  public void generateCallStart(ByteCodeMethod method);
  public void generateCallEnd(ByteCodeMethod method);
  public void postJumpCleanUp(ByteCodeMethod method);
  public RuntimeValue apply(LambdaValue function, RuntimeValue arguments);
}
