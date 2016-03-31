package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.UnspecifiedValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectNilVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.IfStatement;
import rmk35.partIIProject.backend.statements.RuntimeValueStatement;

import lombok.ToString;

@ToString
public class IfBinding extends SintacticBinding
{ @Override
  public Statement applicate(EnvironmentValue environment, RuntimeValue operator, RuntimeValue operands)
  { ConsValue first = operands.accept(new ASTExpectConsVisitor());
    Statement predicate = first.getCar().accept(new ASTConvertVisitor(environment));
    ConsValue second = first.getCdr().accept(new ASTExpectConsVisitor());
    Statement trueCase = second.getCar().accept(new ASTConvertVisitor(environment));

    Statement falseCase;
    if (second.getCdr() instanceof NullValue)
    { falseCase = new RuntimeValueStatement(new UnspecifiedValue());
    } else
    { ConsValue third = second.getCdr().accept(new ASTExpectConsVisitor());
      falseCase = third.getCar().accept(new ASTConvertVisitor(environment));
      third.getCdr().accept(new ASTExpectNilVisitor());
    }
    return new IfStatement(predicate, trueCase, falseCase);
  }
}
