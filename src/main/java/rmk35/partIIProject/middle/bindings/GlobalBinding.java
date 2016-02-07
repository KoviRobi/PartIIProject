package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.GlobalIdentifierStatement;

import lombok.Value;

@Value
public class GlobalBinding extends VariableBinding
{ String identifier;

  @Override
  public IdentifierStatement toStatement(String file, long line, long character)
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
