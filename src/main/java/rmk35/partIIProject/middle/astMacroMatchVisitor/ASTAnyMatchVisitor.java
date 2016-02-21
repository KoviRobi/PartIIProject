package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.middle.Environment;

import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;

public class ASTAnyMatchVisitor extends ASTMatchVisitor
{ @Override
  public Substitution visit(RuntimeValue value)
  { return new Substitution();
  }
}
