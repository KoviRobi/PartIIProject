package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.ApplicationStatement;

import java.util.List;
import java.util.ArrayList;

public abstract class VariableBinding implements Binding
{ public Statement applicate(Environment environment, AST operator, AST operands)
  { List<Statement> applicationList = new ArrayList<>();
    return new ApplicationStatement(this.toStatement(operator.file(), operator.line(), operator.character()),
      operands.accept
        (new ASTListFoldVisitor<List<Statement>>(new ArrayList<>(),
          (list, ast) -> { list.add(ast.accept(new ASTConvertVisitor(environment))); return list; } )));
  }
}
