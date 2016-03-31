package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ThrowableValue;
import rmk35.partIIProject.runtime.ErrorValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.backend.statements.Statement;

import lombok.ToString;

@ToString
public class SyntaxErrorBinding extends SintacticBinding
{ @Override
  public Statement applicate(EnvironmentValue environment, RuntimeValue operator, RuntimeValue operands)
  { throw new ThrowableValue(new ErrorValue(operands));
  }
}
