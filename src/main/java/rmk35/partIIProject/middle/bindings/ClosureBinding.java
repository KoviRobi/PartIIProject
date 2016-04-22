package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.ClosureIdentifierStatement;

import lombok.Value;

@Value
public class ClosureBinding extends VariableBinding
{ String identifier;

  public ClosureBinding(String identifier)
  { this.identifier = identifier;
  }

  @Override
  public IdentifierStatement toStatement(SourceInfo sourceInfo)
  { return new ClosureIdentifierStatement(identifier);
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
