package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.GlobalIdentifierStatement;

import java.util.List;
import java.util.ArrayList;

import lombok.ToString;

@ToString
public class GlobalBinding extends StaticFieldBinding
{ public GlobalBinding(String containingClass, String schemeName, String javaName) { super(containingClass, schemeName, javaName); }

  @Override
  public GlobalIdentifierStatement toStatement(SourceInfo sourceInfo)
  { return new GlobalIdentifierStatement(containingClass, schemeName, javaName);
  }
}
