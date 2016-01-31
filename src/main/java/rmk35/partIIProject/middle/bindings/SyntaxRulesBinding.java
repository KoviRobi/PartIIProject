package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;

import rmk35.partIIProject.backend.statements.Statement;

import lombok.Value;

// This is just a marker binding, hence all the exceptions
// Marker binding means it can be over written, and should never be despatched on
@Value
public class SyntaxRulesBinding implements Binding
{ @Override
  public Statement toStatement(String file, long line, long character)
  { throw new InternalCompilerException("Tried to use toStatement on a syntax-rules binding");
  }

  @Override
  public Statement applicate(Environment useEnvironment, AST operator, AST operands)
  { throw new InternalCompilerException("Tried to use applicate on a syntax-rules binding");
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
