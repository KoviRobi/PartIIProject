package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.LocalIdentifierStatement;

import lombok.Value;

@Value
public class LocalBinding extends VariableBinding
{ String identifier;
  int localIndex;

  @Override
  public IdentifierStatement toStatement(String file, long line, long character)
  { return new LocalIdentifierStatement(identifier, localIndex);
  }

  @Override
  public boolean shouldSaveToClosure()
  { return false;
  }

  @Override
  public Binding subEnvironment()
  { return new ClosureBinding(identifier);
  }
}
