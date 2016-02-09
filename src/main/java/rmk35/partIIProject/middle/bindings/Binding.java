package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.Environment;

import rmk35.partIIProject.backend.statements.Statement;

public interface Binding
{ public Statement toStatement(SourceInfo sourceInfo); // For variable reference
  public Statement applicate(Environment environment, RuntimeValue operator, RuntimeValue operands); // For evaluating as a function

  // For lexical bindings, e.g. want to save local variables but not global ones
  // This is with respect to function body's environment, so parent's
  // locals are closure variables here
  public boolean shouldSaveToClosure();
  // When we enter a new lambda, this transforms the local bindings to closure bindings
  public Binding subEnvironment();
}
