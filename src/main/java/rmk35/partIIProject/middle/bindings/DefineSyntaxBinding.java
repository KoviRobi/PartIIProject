package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;
import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTSyntaxSpecificationVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.UnspecifiedValueStatement;

import lombok.Value;

@Value
public class DefineSyntaxBinding implements Binding
{ @Override
  public Statement toStatement(String file, long line, long character)
  { throw new SyntaxErrorException("Don't know how to use a syntactic variable in a run time context", file, line, character);
  }

  @Override
  public Statement applicate(Environment environment, AST operator, AST operands)
  { Pair<String, SyntaxBinding> binding = operands.accept(new ASTSyntaxSpecificationVisitor(environment));
    environment.addBinding(binding.getFirst(), binding.getSecond());
    return new UnspecifiedValueStatement();
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
