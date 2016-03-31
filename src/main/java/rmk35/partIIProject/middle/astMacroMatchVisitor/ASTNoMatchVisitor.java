package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;

public class ASTNoMatchVisitor extends ASTMatchVisitor
{ @Override
  public Substitution visit(RuntimeValue value)
  { return null;
  }
}
