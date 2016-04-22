package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.backend.statements.FieldIdentifierStatement;

import lombok.ToString;

@ToString
public class FieldBinding extends VariableBinding
{ String containingClass;
  String schemeName;
  String javaName;

  public FieldBinding(String containingClass, String schemeName, String javaName)
  { this.containingClass = containingClass;
    this.schemeName = schemeName;
    this.javaName = javaName;
  }

  @Override
  public FieldIdentifierStatement toStatement(SourceInfo sourceInfo)
  { return new FieldIdentifierStatement(containingClass, schemeName, javaName);
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
