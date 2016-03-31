package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.backend.statements.FieldIdentifierStatement;

import lombok.Value;

@Value
public class FieldBinding extends VariableBinding
{ String containingClass;
  String identifier;

  public FieldBinding(String containingClass, String identifier)
  { this.containingClass = containingClass;
    this.identifier = identifier;
  }

  @Override
  public FieldIdentifierStatement toStatement(SourceInfo sourceInfo)
  { return new FieldIdentifierStatement(containingClass, identifier);
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
  { return true;
  }
}
