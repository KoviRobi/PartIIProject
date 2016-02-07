package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectNilVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.GlobalIdentifierStatement;
import rmk35.partIIProject.backend.statements.DefineStatement;
import rmk35.partIIProject.backend.statements.UnspecifiedValueStatement;

import java.util.List;

import lombok.Value;

@Value
public class DefineBinding implements Binding
{ @Override
  public Statement toStatement(String file, long line, long character)
  { throw new SyntaxErrorException("Don't know how to use a syntactic variable in a run time context", file, line, character);
  }

  // FIXME: so this should call ensureFieldExists and set should (r7rs p.26)
  @Override
  public Statement applicate(Environment environment, AST operator, AST operands)
  { SchemeCons first = operands.accept(new ASTExpectConsVisitor());
    String variable = first.car().accept(new ASTExpectIdentifierVisitor()).getData();
    Binding variableBinding = environment.lookUpSilent(variable);
    SchemeCons second = first.cdr().accept(new ASTExpectConsVisitor());
    Statement expression = second.car().accept(new ASTConvertVisitor(environment));
    second.cdr().accept(new ASTExpectNilVisitor());

    IdentifierStatement variableStatement;
    if (variableBinding != null && variableBinding instanceof IdentifierStatement)
    { variableStatement = (IdentifierStatement) variableBinding;
    } else // Overwrite binding
    { variableStatement = new GlobalIdentifierStatement(variable);
    }

    return new DefineStatement(variableStatement, expression);
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
