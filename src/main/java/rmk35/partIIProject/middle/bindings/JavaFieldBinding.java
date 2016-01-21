package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.ASTListElementVisitor;
import rmk35.partIIProject.middle.ASTListEndVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.JavaFieldStatement;

import java.util.List;

import lombok.Value;

@Value
public class JavaFieldBinding implements Binding
{ @Override
  public Statement toStatement(String file, long line, long character)
  { throw new SyntaxErrorException("Don't know how to use a syntactic variable in a run time context", file, line, character);
  }

  @Override
  public Statement applicate(Environment environment, AST arguments, String file, long line, long character)
  { SchemeCons first = arguments.accept(new ASTListElementVisitor());
    Statement object = first.car().accept(new ASTConvertVisitor(environment));
    SchemeCons second = first.cdr().accept(new ASTListElementVisitor());
    Statement fieldName = second.car().accept(new ASTConvertVisitor(environment));
    second.cdr().accept(new ASTListEndVisitor());
    return new JavaFieldStatement(object, fieldName);
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
