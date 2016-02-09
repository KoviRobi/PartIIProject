package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectNilVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.GlobalIdentifierStatement;
import rmk35.partIIProject.backend.statements.SetStatement;
import rmk35.partIIProject.backend.statements.UnspecifiedValueStatement;

import java.util.List;

import lombok.ToString;

@ToString
public class SetBinding extends SintacticBinding
{ @Override
  public Statement applicate(Environment environment, RuntimeValue operator, RuntimeValue operands)
  { ConsValue first = operands.accept(new ASTExpectConsVisitor());
    String variable = first.getCar().accept(new ASTExpectIdentifierVisitor()).getValue();
    Binding variableBinding = environment.lookUpSilent(variable);
    ConsValue second = first.getCdr().accept(new ASTExpectConsVisitor());
    Statement expression = second.getCar().accept(new ASTConvertVisitor(environment));
    second.getCdr().accept(new ASTExpectNilVisitor());

    IdentifierStatement variableStatement;
    if (variableBinding != null && variableBinding instanceof IdentifierStatement)
    { variableStatement = (IdentifierStatement) variableBinding;
    } else // Overwrite binding
    { variableStatement = new GlobalIdentifierStatement(variable);
    }

    return new SetStatement(variableStatement, expression);
  }
}
