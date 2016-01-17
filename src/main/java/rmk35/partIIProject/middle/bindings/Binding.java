package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.backend.statements.Statement;

public interface Binding
{ public Statement toStatement();
  public boolean shouldSaveToClosure(); // This is with respect to function body's environment
  public Binding subEnvironment(); // When we enter a new lambda, this transforms the local bindings to closure bindings
}
