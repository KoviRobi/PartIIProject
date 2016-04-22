package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.ByteCodeMethod;

public interface TailCallSettings
{ public void generateContinuation(ByteCodeMethod method);
  public void generateCallStart(ByteCodeMethod method);
  public void generateCallEnd(ByteCodeMethod method);
}
