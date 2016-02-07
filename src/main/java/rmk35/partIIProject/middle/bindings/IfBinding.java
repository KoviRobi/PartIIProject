package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectNilVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.IfStatement;
import rmk35.partIIProject.backend.statements.UnspecifiedValueStatement;

import java.util.List;

import lombok.Value;

@Value
public class IfBinding implements Binding
{ @Override
  public Statement toStatement(String file, long line, long character)
  { throw new SyntaxErrorException("Don't know how to use a syntactic variable in a run time context", file, line, character);
  }

  @Override
  public Statement applicate(Environment environment, AST operator, AST operands)
  { SchemeCons first = operands.accept(new ASTExpectConsVisitor());
    Statement predicate = first.car().accept(new ASTConvertVisitor(environment));
    SchemeCons second = first.cdr().accept(new ASTExpectConsVisitor());
    Statement trueCase = second.car().accept(new ASTConvertVisitor(environment));

    Statement falseCase;
    if (second.cdr() instanceof SchemeNil)
    { falseCase = new UnspecifiedValueStatement();
    } else
    { SchemeCons third = second.cdr().accept(new ASTExpectConsVisitor());
      falseCase = third.car().accept(new ASTConvertVisitor(environment));
      third.cdr().accept(new ASTExpectNilVisitor());
    }
    return new IfStatement(predicate, trueCase, falseCase);
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
