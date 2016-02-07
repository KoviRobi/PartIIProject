package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.ClosureIdentifierStatement;

import lombok.Value;

@Value
public class ClosureBinding extends VariableBinding
{ String identifier;

  @Override
  public IdentifierStatement toStatement(String file, long line, long character)
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
