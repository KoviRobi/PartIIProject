package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTImproperListFoldVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;

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
  public Statement applicate(Environment environment, AST operator, AST operands)
  { SchemeCons first = operands.accept(new ASTExpectConsVisitor());
    Environment bodyEnvironment = new Environment(environment, /* subEnvironment */ true);
    List<String> formals = first.car().accept(new ASTImproperListFoldVisitor<List<String>>(new ArrayList<String>(),
      (List<String> list, AST ast) -> { list.add(ast.accept(new ASTExpectIdentifierVisitor()).getData()); return list; } ));
    bodyEnvironment.addLocalVariables(formals);

    Statement body = first.cdr().accept
      (new ASTListFoldVisitor<Statement>(null,
        (previous, ast) -> ast.accept(new ASTConvertVisitor(bodyEnvironment)) ));
    if (body == null)
    { throw new SyntaxErrorException("Empty lambda body", operator.file(), operator.line(), operator.character());
    }

    List<IdentifierStatement> closureVariables = new ArrayList<>();
    // Look up in the current environment, as these will be used to get the be variable value
    // to save them in the created function's closure
    for (String variable : body.getFreeIdentifiers())
    { if (bodyEnvironment.lookUp(variable).shouldSaveToClosure()) // Note the use of bodyEnvironment here and environment below
      { closureVariables.add((IdentifierStatement)environment.lookUpAsStatement(variable, operator.file(), operator.line(), operator.character()));
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
