package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTListFoldVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.ApplicationStatement;

import java.util.List;
import java.util.ArrayList;

public abstract class VariableBinding implements Binding
{ public Statement applicate(Environment environment, AST arguments, String file, long line, long character)
  { List<Statement> applicationList = new ArrayList<>();
    applicationList.add(this.toStatement(file, line, character));
    return new ApplicationStatement(arguments.accept
      (new ASTListFoldVisitor<List<Statement>>(applicationList,
        (list, ast) -> { list.add(ast.accept(new ASTConvertVisitor(environment))); return list; } )));
  }
}
