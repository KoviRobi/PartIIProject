package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;

import rmk35.partIIProject.backend.statements.Statement;

import lombok.Value;

@Value
public class ASTSyntaxSpecificationVisitor extends ASTVisitor<Object>
{ Environment environment;

  public ASTSyntaxSpecificationVisitor(Environment environment)
  { this.environment = environment;
  }

  @Override
  public Object visit(SchemeCons consCell)
  { return null; /* NEXT 3 */
  }

  @Override
  public Object visit(SchemeNil nil)
  { return null; /* NEXT 3 */
  }

  @Override
  public Object visit(SchemeIdentifier identifier)
  { throw new SyntaxErrorException("I was expecting a proper list", identifier.file(), identifier.line(), identifier.character());
  }

  @Override
  public Object visit(SchemeLiteral object)
  { throw new SyntaxErrorException("I was expecting a proper list", object.file(), object.line(), object.character());
  }

  @Override
  public Object visit(SchemeLabelReference reference)
  { throw new SyntaxErrorException("I was expecting a proper list", reference.file(), reference.line(), reference.character());
  }

  @Override
  public Object visit(SchemeLabelledData data)
  { throw new SyntaxErrorException("I was expecting a proper list", data.file(), data.line(), data.character());
  }
}
