package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.JavaMethodStatement;

import java.util.List;
import java.util.ArrayList;

import lombok.ToString;

@ToString
public class JavaMethodBinding extends SintacticBinding
{ @Override
  public Statement applicate(EnvironmentValue environment, RuntimeValue operator, RuntimeValue operands)
  { ConsValue first = operands.accept(new ASTExpectConsVisitor());
    Statement object =  first.getCar().accept(new ASTConvertVisitor(environment));
    ConsValue second =  first.getCdr().accept(new ASTExpectConsVisitor());
    Statement methodName =  second.getCar().accept(new ASTConvertVisitor(environment));
    List<Statement> methodArguments =  second.getCdr().accept
      (new ASTListFoldVisitor<List<Statement>>(new ArrayList<>(),
        (list, ast) -> { list.add(ast.accept(new ASTConvertVisitor(environment))); return list; } ));
    return new JavaMethodStatement(object, methodName, methodArguments);
  }
}
