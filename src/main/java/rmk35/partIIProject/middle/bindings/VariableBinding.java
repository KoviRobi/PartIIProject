package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.ApplicationStatement;

import java.util.List;
import java.util.ArrayList;

public abstract class VariableBinding implements Binding
{ public Statement applicate(Environment environment, RuntimeValue operator, RuntimeValue operands)
  { List<Statement> applicationList = new ArrayList<>();
    return new ApplicationStatement(this.toStatement(operator.getSourceInfo()),
      operands.accept
        (new ASTListFoldVisitor<List<Statement>>(new ArrayList<>(),
          (list, ast) -> { list.add(ast.accept(new ASTConvertVisitor(environment))); return list; } )));
  }
}
