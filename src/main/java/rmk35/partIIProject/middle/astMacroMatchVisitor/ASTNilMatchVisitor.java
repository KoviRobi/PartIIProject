package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.NullValue;

import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;

public class ASTNilMatchVisitor extends ASTNoMatchVisitor
{ @Override
  public Substitution visit(NullValue nil)
  { return new Substitution();
  }

  @Override
  public String toString()
  { return "()";
  }
}
