package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.NullValue;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectNilVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.IfStatement;
import rmk35.partIIProject.backend.statements.UnspecifiedValueStatement;

import lombok.ToString;

@ToString
public class IfBinding extends SintacticBinding
{ @Override
  public Statement applicate(Environment environment, RuntimeValue operator, RuntimeValue operands)
  { ConsValue first = operands.accept(new ASTExpectConsVisitor());
    Statement predicate = first.getCar().accept(new ASTConvertVisitor(environment));
    ConsValue second = first.getCdr().accept(new ASTExpectConsVisitor());
    Statement trueCase = second.getCar().accept(new ASTConvertVisitor(environment));

    Statement falseCase;
    if (second.getCdr() instanceof NullValue)
    { falseCase = new UnspecifiedValueStatement();
    } else
    { ConsValue third = second.getCdr().accept(new ASTExpectConsVisitor());
      falseCase = third.getCar().accept(new ASTConvertVisitor(environment));
      third.getCdr().accept(new ASTExpectNilVisitor());
    }
    return new IfStatement(predicate, trueCase, falseCase);
  }
}
