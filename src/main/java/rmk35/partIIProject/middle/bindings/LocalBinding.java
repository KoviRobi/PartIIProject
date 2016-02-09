package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.LocalIdentifierStatement;

import lombok.Value;

@Value
public class LocalBinding extends VariableBinding
{ String identifier;
  int localIndex;

  public LocalBinding(String identifier, int localIndex)
  { this.identifier = identifier;
    this.localIndex = localIndex;
  }

  @Override
  public IdentifierStatement toStatement(SourceInfo sourceInfo)
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
