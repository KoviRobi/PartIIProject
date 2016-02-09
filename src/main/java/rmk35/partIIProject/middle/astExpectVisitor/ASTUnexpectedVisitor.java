package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.SelfquotingValue;

import rmk35.partIIProject.middle.ASTVisitor;

public class ASTUnexpectedVisitor<T> extends ASTVisitor<T>
{ @Override
  public T visit(ConsValue consCell) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was not expecting a cons cell", consCell.getSourceInfo());
  }

  @Override
  public T visit(IdentifierValue identifier) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was not expecting an identifier", identifier.getSourceInfo());
  }

  @Override
  public T visit(NullValue nil)
  { throw new SyntaxErrorException("I was not expecting nil", nil.getSourceInfo());
  }

  @Override
  public T visit(SelfquotingValue object) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was not expecting a literal", object.getSourceInfo());
  }
}
