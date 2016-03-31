package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.backend.statements.Statement;

public abstract class SintacticBinding implements Binding
{ @Override
  public Statement toStatement(SourceInfo sourceInfo)
  { throw new SyntaxErrorException("Don't know how to use a syntactic variable in a runtime context", sourceInfo);
  }

  @Override
  public boolean shouldSaveToClosure()
  { return false;
  }

  @Override
  public Binding subEnvironment()
  { return this;
  }

  @Override
  public boolean runtime()
  { return false;
  }
}
