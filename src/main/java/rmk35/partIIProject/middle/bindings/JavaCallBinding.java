package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.JavaCallStatement;

import java.util.List;
import java.util.ArrayList;

import lombok.ToString;

@ToString
public class JavaCallBinding extends SintacticBinding
{ @Override
  public Statement applicate(Environment environment, RuntimeValue operator, RuntimeValue operands)
  { ConsValue first = operands.accept(new ASTExpectConsVisitor());
    Statement method = first.getCar().accept(new ASTConvertVisitor(environment));
    ConsValue second = first.getCdr().accept(new ASTExpectConsVisitor());
    Statement object = second.getCar().accept(new ASTConvertVisitor(environment));
    List<Statement> javaArguments = second.getCdr().accept
      (new ASTListFoldVisitor<List<Statement>>(new ArrayList<>(),
        (list, ast) -> { list.add(ast.accept(new ASTConvertVisitor(environment))); return list; } ));
    return new JavaCallStatement(method, object, javaArguments);
  }
}
