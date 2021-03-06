package rmk35.partIIProject.runtime.numbers;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.ASTVisitor;

public abstract class ComplexValue extends NumberValue
{ @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }
}
