package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTListElementVisitor;
import rmk35.partIIProject.middle.ASTLambdaFormalsVisitor;
import rmk35.partIIProject.middle.ASTBeginVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.LambdaStatement;

import java.util.List;
import java.util.ArrayList;

import lombok.Value;

@Value
public class LambdaSyntaxBinding implements Binding
{ @Override
  public Statement toStatement(String file, long line, long character)
  { throw new SyntaxErrorException("Don't know how to use a syntactic variable in a run time context", file, line, character);
  }

  @Override
  public Statement applicate(Environment environment, AST arguments, String file, long line, long character)
  { SchemeCons first = arguments.accept(new ASTListElementVisitor());
    Environment bodyEnvironment = new Environment(environment);
    List<String> formals = first.car().accept(new ASTLambdaFormalsVisitor());
    bodyEnvironment.addLocalVariables(formals);

    List<Statement> bodyList = first.cdr().accept(new ASTBeginVisitor(bodyEnvironment));
    if (bodyList.size() == 0)
    { throw new SyntaxErrorException("Empty lambda body", file, line, character);
    }
     Statement body = bodyList.get(bodyList.size() - 1);

    List<IdentifierStatement> closureVariables = new ArrayList<>();
    // Look up in the current environment, as these will be used to get the be variable value
    // to save them in the created function's closure
    for (String variable : body.getFreeIdentifiers())
    { if (bodyEnvironment.lookUp(variable).shouldSaveToClosure()) // Note the use of bodyEnvironment here and environment below
      { closureVariables.add((IdentifierStatement)environment.lookUpAsStatement(variable, file, line, character));
      }
    }

    return new LambdaStatement(formals, closureVariables, body);
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
