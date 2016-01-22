package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTListElementVisitor;
import rmk35.partIIProject.middle.ASTListFoldVisitor;
import rmk35.partIIProject.middle.ASTSyntaxSpecificationVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;

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
  { SchemeCons first = arguments.accept(new ASTListElementVisitor());
    //  Copy environment for lexical effect
    Environment letEnvironment = new Environment(environment, false);

    // This modifies the environment
    first.car().accept(new ASTListFoldVisitor<Object>(null,
      (Object previous, AST ast) -> ast.accept(new ASTSyntaxSpecificationVisitor(letEnvironment)) ));

    return first.cdr().accept(new ASTConvertVisitor(letEnvironment));
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
