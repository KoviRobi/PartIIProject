package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.StaticFieldIdentifierStatement;

import lombok.ToString;

@ToString
public class StaticFieldBinding extends VariableBinding
{ String containingClass;
  String schemeName;
  String javaName;

  public StaticFieldBinding(String containingClass, String schemeName, String javaName)
  { this.containingClass = containingClass;
    this.schemeName = schemeName;
    this.javaName = javaName;
  }

  @Override
  public StaticFieldIdentifierStatement toStatement(SourceInfo sourceInfo)
  { return new StaticFieldIdentifierStatement(containingClass, schemeName, javaName);
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
