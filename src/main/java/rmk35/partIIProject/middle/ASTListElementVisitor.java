package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;
import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;

import lombok.Value;

@Value
public class ASTListElementVisitor extends ASTVisitor<SchemeCons>
{ @Override
  public SchemeCons visit(SchemeCons consCell) throws SyntaxErrorException
  { return consCell;
  }

  @Override
  public SchemeCons visit(SchemeNil nil)
  { throw new SyntaxErrorException("List was too short", nil.file(), nil.line(), nil.character());
  }

  @Override
  public SchemeCons visit(SchemeIdentifier identifier) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was expecting a proper list", identifier.file(), identifier.line(), identifier.character());
  }

  @Override
  public SchemeCons visit(SchemeLiteral object) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was expecting a proper list", object.file(), object.line(), object.character());
  }

  @Override
  public SchemeCons visit(SchemeLabelReference reference) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was expecting a proper list", reference.file(), reference.line(), reference.character());
  }

  @Override
  public SchemeCons visit(SchemeLabelledData data) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was expecting a proper list", data.file(), data.line(), data.character());
  }
}