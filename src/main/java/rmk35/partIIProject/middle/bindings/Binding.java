package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;

import rmk35.partIIProject.backend.statements.Statement;

public interface Binding
{ public Statement toStatement(String file, long line, long character); // For variable reference
  public Statement applicate(Environment environment, AST operator, AST operands); // For evaluating as a function

  // For lexical bindings, e.g. want to save local variables but not global ones
  // This is with respect to function body's environment, so parent's
  // locals are closure variables here
  public boolean shouldSaveToClosure();
  // When we enter a new lambda, this transforms the local bindings to closure bindings
  public Binding subEnvironment();
}
