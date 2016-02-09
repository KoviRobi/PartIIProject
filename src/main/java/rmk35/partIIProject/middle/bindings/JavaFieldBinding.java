package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectNilVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.JavaFieldStatement;

import java.util.List;

import lombok.ToString;

@ToString
public class JavaFieldBinding extends SintacticBinding
{ @Override
  public Statement applicate(Environment environment, RuntimeValue operator, RuntimeValue operands)
  { ConsValue first = operands.accept(new ASTExpectConsVisitor());
    Statement object = first.getCar().accept(new ASTConvertVisitor(environment));
    ConsValue second = first.getCdr().accept(new ASTExpectConsVisitor());
    Statement fieldName = second.getCar().accept(new ASTConvertVisitor(environment));
    second.getCdr().accept(new ASTExpectNilVisitor());
    return new JavaFieldStatement(object, fieldName);
  }
}
