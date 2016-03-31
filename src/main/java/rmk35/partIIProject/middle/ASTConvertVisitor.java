package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.SelfquotingValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.RuntimeValueStatement;

import lombok.Data;

/* This is the main visitor, it converts the frontend's RuntimeValue to the backend's Statement */

@Data
public class ASTConvertVisitor extends ASTVisitor<Statement>
{ EnvironmentValue environment; /* STATE */

  public ASTConvertVisitor(EnvironmentValue environment)
  { this.environment =  environment;
  }

  @Override
  public Statement visit(ConsValue consCell)
  { return consCell.getCar().accept(new ASTApplicationVisitor(environment, consCell.getCdr()));
  }

  @Override
  public Statement visit(IdentifierValue identifier)
  { return environment.getOrGlobal(identifier.getValue()).toStatement(identifier.getSourceInfo());
  }

  @Override
  public Statement visit(NullValue nil)
  { throw new SyntaxErrorException("Don't know what to do with nil", nil.getSourceInfo());
  }

  @Override
  protected Statement visit(SelfquotingValue object)
  { return new RuntimeValueStatement(object);
  }
}
