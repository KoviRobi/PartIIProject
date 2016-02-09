package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.Environment;

import rmk35.partIIProject.backend.statements.Statement;

import lombok.ToString;

// This is just a marker binding, hence all the exceptions
// Marker binding means it can be over written, and should never be despatched on
@ToString
public class MarkerBinding implements Binding
{ @Override
  public Statement toStatement(SourceInfo sourceInfo)
  { throw new SyntaxErrorException("Tried to use toStatement on an ellipsis binding", sourceInfo);
  }

  @Override
  public Statement applicate(Environment useEnvironment, RuntimeValue operator, RuntimeValue operands)
  { throw new SyntaxErrorException("Tried to use applicate on an ellipsis binding", operator.getSourceInfo());
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
