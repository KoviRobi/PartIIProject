package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.LocalIdentifierStatement;

import lombok.Value;

@Value
public class LocalBinding implements Binding
{ String identifier;
  int localIndex;

  @Override
  public Statement toStatement()
  { return new LocalIdentifierStatement(identifier, localIndex);
  }
}
