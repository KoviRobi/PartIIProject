package rmk35.partIIProject.runtime;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.TrampolineValue;

import rmk35.partIIProject.middle.ASTVisitor;

public class TrampolineVisitor extends ASTVisitor<Object>
{ @Override
  public Object visit(TrampolineValue trampoline) { return trampoline.call(); }

  @Override
  public Object visit(RuntimeValue value) { return value; }
}
