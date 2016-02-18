package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.middle.ASTVisitor;

public class ASTUnexpectedVisitor<T> extends ASTVisitor<T>
{ @Override
  public T visit(RuntimeValue value) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was not expecting " + value, value.getSourceInfo());
  }
}
