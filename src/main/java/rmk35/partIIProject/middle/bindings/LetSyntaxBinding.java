package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;

import rmk35.partIIProject.backend.statements.Statement;

import lombok.Value;

@Value
public class LetSyntaxBinding implements Binding
{ @Override
  public Statement toStatement(String file, long line, long character)
  { throw new SyntaxErrorException("Don't know how to use a syntactic variable in a run time context", file, line, character);
  }
  
  @Override
  public Statement applicate(Environment environment, AST arguments, String file, long line, long character)
  { return null; // NEXT 1
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
