package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.GlobalIdentifierStatement;

import lombok.Value;

@Value
public class GlobalBinding extends VariableBinding
{ String identifier;

  public GlobalBinding(String identifier)
  { this.identifier = identifier;
  }

  @Override
  public GlobalIdentifierStatement toStatement(SourceInfo sourceInfo)
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

  @Override
  public boolean runtime()
  { return false;
  }
}
