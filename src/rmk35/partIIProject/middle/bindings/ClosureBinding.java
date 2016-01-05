package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.LocalIdentifierStatement;

import lombok.Value;

@Value
public class ClosureBinding implements Binding
{ String identifier;

  @Override
  public Statement toStatement()
  { return new ClosureIdentifierStatement(identifier);
  }
}
