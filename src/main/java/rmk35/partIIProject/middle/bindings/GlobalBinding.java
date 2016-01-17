package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.GlobalIdentifierStatement;

import lombok.Value;

@Value
public class GlobalBinding implements Binding
{ String identifier;

  @Override
  public Statement toStatement()
  { return new GlobalIdentifierStatement(identifier);
  }

  @Override
  public boolean shouldSaveToClosure()
  { return false;
  }

  @Override
  public Binding subEnvironment()
  { return this;
  }
}
