package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;

import rmk35.partIIProject.backend.statements.Statement;

import java.util.List;
import java.util.ArrayList;

import lombok.Value;

/* This is the main visitor, it converts the frontend's AST to the backend's Statement */

@Value
public class ASTBeginVisitor extends ASTVisitor<List<Statement>>
{ Environment environment; /* STATE */
  List<Statement> returnValue; /* STATE */

  public ASTBeginVisitor(Environment environment)
  { this(environment, new ArrayList<>());
  }
  public ASTBeginVisitor(Environment environment, List<Statement> start)
  { this.environment =  environment;
    this.returnValue = start;
  }

  @Override
  public List<Statement> visit(SchemeCons consCell)
  { returnValue.add(consCell.car().accept(new ASTConvertVisitor(environment)));
    return consCell.cdr().accept(this);
  }

  @Override
  public List<Statement> visit(SchemeNil nil)
  { return returnValue;
  }

  @Override
  public List<Statement> visit(SchemeIdentifier identifier)
  { throw new SyntaxErrorException("I was expecting a proper list", identifier.file(), identifier.line(), identifier.character());
  }

  @Override
  public List<Statement> visit(SchemeLiteral object)
  { throw new SyntaxErrorException("I was expecting a proper list", object.file(), object.line(), object.character());
  }

  @Override
  public List<Statement> visit(SchemeLabelReference reference)
  { throw new SyntaxErrorException("I was expecting a proper list", reference.file(), reference.line(), reference.character());
  }

  @Override
  public List<Statement> visit(SchemeLabelledData data)
  { throw new SyntaxErrorException("I was expecting a proper list", data.file(), data.line(), data.character());
  }
}
