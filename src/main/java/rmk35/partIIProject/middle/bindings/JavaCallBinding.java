package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.JavaCallStatement;

import java.util.List;
import java.util.ArrayList;

import lombok.Value;

@Value
public class JavaCallBinding implements Binding
{ @Override
  public Statement toStatement(String file, long line, long character)
  { throw new SyntaxErrorException("Don't know how to use a syntactic variable in a run time context", file, line, character);
  }

  @Override
  public Statement applicate(Environment environment, AST operator, AST operands)
  { SchemeCons first = operands.accept(new ASTExpectConsVisitor());
    Statement method = first.car().accept(new ASTConvertVisitor(environment));
    SchemeCons second = first.cdr().accept(new ASTExpectConsVisitor());
    Statement object = second.car().accept(new ASTConvertVisitor(environment));
    List<Statement> javaArguments = second.cdr().accept
      (new ASTListFoldVisitor<List<Statement>>(new ArrayList<>(),
        (list, ast) -> { list.add(ast.accept(new ASTConvertVisitor(environment))); return list; } ));
    return new JavaCallStatement(method, object, javaArguments);
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
