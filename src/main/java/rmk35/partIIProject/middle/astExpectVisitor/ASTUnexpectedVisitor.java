package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;

import rmk35.partIIProject.middle.ASTVisitor;

public class ASTUnexpectedVisitor<T> extends ASTVisitor<T>
{ @Override
  public T visit(SchemeCons consCell) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was not expecting a cons cell", consCell.file(), consCell.line(), consCell.character());
  }

  @Override
  public T visit(SchemeNil nil)
  { throw new SyntaxErrorException("I was not expecting nil", nil.file(), nil.line(), nil.character());
  }

  @Override
  public T visit(SchemeIdentifier identifier) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was not expecting an identifier", identifier.file(), identifier.line(), identifier.character());
  }

  @Override
  public T visit(SchemeLiteral object) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was not expecting a literal", object.file(), object.line(), object.character());
  }

  @Override
  public T visit(SchemeLabelReference reference) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was not expecting a label reference", reference.file(), reference.line(), reference.character());
  }

  @Override
  public T visit(SchemeLabelledData data) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was not expecting a labelled data", data.file(), data.line(), data.character());
  }
}
