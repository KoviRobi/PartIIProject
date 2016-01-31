package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;

import rmk35.partIIProject.backend.statements.Statement;

import lombok.Value;

// This is just a marker binding, hence all the exceptions
// Marker binding means it can be over written, and should never be despatched on
@Value
public class EllipsisBinding implements Binding
{ @Override
  public Statement toStatement(String file, long line, long character)
  { throw new InternalCompilerException("Tried to use toStatement on an ellipsis binding");
  }

  @Override
  public Statement applicate(Environment useEnvironment, AST operator, AST operands)
  { throw new InternalCompilerException("Tried to use applicate on an ellipsis binding");
  }

  @Override
  public boolean shouldSaveToClosure()
  { throw new InternalCompilerException("Tried to use shouldSaveToClosure on an ellipsis binding");
  }

  @Override
  public Binding subEnvironment()
  { throw new InternalCompilerException("Tried to use subEnvironment on an ellipsis binding");
  }
}
