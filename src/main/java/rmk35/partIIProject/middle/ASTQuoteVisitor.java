package rmk35.partIIProject.middle;

import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.SelfquotingValue;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.RuntimeValueStatement;

public class ASTQuoteVisitor extends ASTVisitor<Statement>
{ @Override
  public Statement visit(ConsValue object)
  { return new RuntimeValueStatement(object);
  }

  @Override
  public Statement visit(IdentifierValue object)
  { return new RuntimeValueStatement(object);
  }

  @Override
  public Statement visit(NullValue object)
  { return new RuntimeValueStatement(object);
  }

  @Override
  protected Statement visit(SelfquotingValue object)
  { return new RuntimeValueStatement(object);
  }
}
