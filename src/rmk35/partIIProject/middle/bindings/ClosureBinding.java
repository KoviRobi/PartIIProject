package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.ClosureIdentifierStatement;

import lombok.Value;

@Value
public class ClosureBinding implements Binding
{ String identifier;

  @Override
  public Statement toStatement()
  { return new ClosureIdentifierStatement(identifier);
  }

  @Override
  public boolean shouldSaveToClosure()
  { return true;
  }

  @Override
  public Binding subEnvironment()
  { return this;
  }
}
