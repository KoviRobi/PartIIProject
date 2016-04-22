package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectNilVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.GlobalIdentifierStatement;
import rmk35.partIIProject.backend.statements.DefineStatement;

import java.util.List;

import lombok.ToString;

@ToString
public class DefineBinding extends SintacticBinding
{ @Override
  public Statement applicate(EnvironmentValue environment, OutputClass outputClass, MainClass mainClass, RuntimeValue operator, RuntimeValue operands)
  { ConsValue first = operands.accept(new ASTExpectConsVisitor());
    String variable = first.getCar().accept(new ASTExpectIdentifierVisitor()).getValue();
    Binding variableBinding = environment.getOrNull(variable);
    ConsValue second = first.getCdr().accept(new ASTExpectConsVisitor());
    Statement expression = second.getCar().accept(new ASTConvertVisitor(environment, outputClass, mainClass));
    second.getCdr().accept(new ASTExpectNilVisitor());

    if (variableBinding == null || ! (variableBinding instanceof VariableBinding))
    { environment.addGlobalVariable(mainClass, variable);
    }

    IdentifierStatement variableStatement = (IdentifierStatement) environment.getOrGlobal(mainClass, variable).toStatement(operator.getSourceInfo());
    return new DefineStatement(variableStatement, expression);
  }
}
