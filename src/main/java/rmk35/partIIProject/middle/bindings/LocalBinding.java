package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.LocalIdentifierStatement;

import java.util.List;
import java.util.ArrayList;

import lombok.ToString;

@ToString
public class LocalBinding extends FieldBinding
{ int inParentNo;

  public LocalBinding(String containingClass, String schemeName, String javaName) { this(containingClass, schemeName, javaName, 0); }
  public LocalBinding(String containingClass, String schemeName, String javaName, int inParentNo)
  { super(containingClass, schemeName, javaName);
    this.inParentNo = inParentNo;
  }

  @Override
  public LocalIdentifierStatement toStatement(SourceInfo sourceInfo)
  { return new LocalIdentifierStatement(containingClass, schemeName, javaName, inParentNo);
  }

  @Override
  public Binding subEnvironment()
  { return new LocalBinding(containingClass, schemeName, javaName, inParentNo+1);
  }
}
