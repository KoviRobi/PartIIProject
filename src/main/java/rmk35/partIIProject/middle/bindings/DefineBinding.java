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
import rmk35.partIIProject.backend.statements.DefineStatement;
import rmk35.partIIProject.backend.statements.UnspecifiedValueStatement;

import java.util.List;

import lombok.ToString;

@ToString
public class DefineBinding extends SintacticBinding
{ @Override
  public Statement applicate(Environment environment, RuntimeValue operator, RuntimeValue operands)
  { ConsValue first = operands.accept(new ASTExpectConsVisitor());
    String variable = first.getCar().accept(new ASTExpectIdentifierVisitor()).getValue();
    Binding variableBinding = environment.lookUpSilent(variable);
    ConsValue second = first.getCdr().accept(new ASTExpectConsVisitor());
    Statement expression = second.getCar().accept(new ASTConvertVisitor(environment));
    second.getCdr().accept(new ASTExpectNilVisitor());

    IdentifierStatement variableStatement;
    if (variableBinding != null && variableBinding instanceof VariableBinding)
    { variableStatement = (IdentifierStatement) environment.lookUpAsStatement(variable, operator.getSourceInfo());
    } else // Overwrite binding
    { environment.addBinding(variable, new GlobalBinding(variable));
      variableStatement = new GlobalIdentifierStatement(variable);
    }

    return new DefineStatement(variableStatement, expression);
  }
}
