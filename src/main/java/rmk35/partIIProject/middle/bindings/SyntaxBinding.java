package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.backend.statements.Statement;

import lombok.Value;

@Value
public class SyntaxBinding implements Binding
{ @Override
  public Statement toStatement()
  { throw new UnsupportedOperationException("Don't know how to turn a SyntaxBinding to a Statement, ToDo?");
  }

  @Override
  public boolean shouldSaveToClosure()
  { return false;
  }

  @Override
  public Binding subEnvironment()
  { return this;
  }
}
