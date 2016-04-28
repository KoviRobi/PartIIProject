package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.statements.Statement;

public interface Binding
{ public Statement toStatement(SourceInfo sourceInfo); // For variable reference
  public Statement applicate(EnvironmentValue environment, OutputClass outputClass, MainClass mainClass, RuntimeValue operator, RuntimeValue operands); // For evaluating as a function

  // When we enter a new lambda, this increments the number of levels we go up for a local (field backed) variable
  public Binding subEnvironment();
  // When we import from a different environment, if it is a runtime binding we want to copy it
  public boolean runtime();
  public boolean equals(Object other);
}
