package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;
import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;

import rmk35.partIIProject.middle.bindings.Binding;

import lombok.Value;

@Value
public class ASTListEndVisitor extends ASTVisitor<SchemeNil>
{ @Override
  public SchemeNil visit(SchemeCons consCell) throws SyntaxErrorException
  { throw new SyntaxErrorException("List was too long", consCell.file(), consCell.line(), consCell.character());
  }

  @Override
  public SchemeNil visit(SchemeNil nil)
  { return nil;
  }

  @Override
  public SchemeNil visit(SchemeIdentifier identifier) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was expecting a proper list", identifier.file(), identifier.line(), identifier.character());
  }

  @Override
  public SchemeNil visit(SchemeLiteral object) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was expecting a proper list", object.file(), object.line(), object.character());
  }

  @Override
  public SchemeNil visit(SchemeLabelReference reference) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was expecting a proper list", reference.file(), reference.line(), reference.character());
  }

  @Override
  public SchemeNil visit(SchemeLabelledData data) throws SyntaxErrorException
  { throw new SyntaxErrorException("I was expecting a proper list", data.file(), data.line(), data.character());
  }
}