package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectNilVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.JavaStaticFieldStatement;

import java.util.List;

import lombok.Value;

@Value
public class JavaStaticFieldBinding implements Binding
{ @Override
  public Statement toStatement(String file, long line, long character)
  { throw new SyntaxErrorException("Don't know how to use a syntactic variable in a run time context", file, line, character);
  }

  @Override
  public Statement applicate(Environment environment, AST operator, AST operands)
  { SchemeCons first = operands.accept(new ASTExpectConsVisitor());
    Statement object = first.car().accept(new ASTConvertVisitor(environment));
    SchemeCons second = first.cdr().accept(new ASTExpectConsVisitor());
    Statement fieldName = second.car().accept(new ASTConvertVisitor(environment));
    second.cdr().accept(new ASTExpectNilVisitor());
    return new JavaStaticFieldStatement(object, fieldName);
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
