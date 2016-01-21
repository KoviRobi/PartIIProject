package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
// Literals
import rmk35.partIIProject.frontend.AST.SchemeNumber;

import rmk35.partIIProject.middle.bindings.Binding;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.NumberValueStatement;

import lombok.Value;

import java.util.List;

/* This is the main visitor, it converts the frontend's AST to the backend's Statement */

@Value
public class ASTConvertVisitor extends ASTVisitor<Statement>
{ Environment environment; /* STATE */

  public ASTConvertVisitor(Environment environment)
  { this.environment =  environment;
  }

  @Override
  public Statement visit(SchemeCons consCell)
  { return consCell.car().accept(new ASTApplicationVisitor(environment, consCell.cdr()));
  }

  @Override
  public Statement visit(SchemeNil nil)
  { throw new SyntaxErrorException("Don't know what to do with nil", nil.file(), nil.line(), nil.character());
  }

  @Override
  public Statement visit(SchemeIdentifier identifier)
  { return environment.lookUpAsStatement(identifier.getData());
  }

  @Override
  public Statement visit(SchemeLiteral object)
  { throw new UnsupportedOperationException();
  }

  @Override
  public Statement visit(SchemeNumber number)
  { return new NumberValueStatement(Integer.parseInt(number.getData()));
  }

  @Override
  public Statement visit(SchemeLabelReference reference)
  { throw new SyntaxErrorException("Unexpected label reference", reference.file(), reference.line(), reference.character());
  }

  @Override
  public Statement visit(SchemeLabelledData data)
  { throw new SyntaxErrorException("Don't know how to handle labelled data in non-literal position", data.file(), data.line(), data.character());
  }
}
