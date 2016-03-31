package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTImproperListFoldVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.LambdaStatement;
import rmk35.partIIProject.backend.statements.BeginStatement;

import java.util.List;
import java.util.ArrayList;

import lombok.ToString;

@ToString
public class LambdaSyntaxBinding extends SintacticBinding
{ @Override
  public Statement applicate(EnvironmentValue environment, RuntimeValue operator, RuntimeValue operands)
  { ConsValue first = operands.accept(new ASTExpectConsVisitor());
    EnvironmentValue bodyEnvironment = environment.subEnvironment();
    List<String> formals = first.getCar().accept(new ASTImproperListFoldVisitor<List<String>>(new ArrayList<String>(),
      (List<String> list, RuntimeValue ast) -> { list.add(ast.accept(new ASTExpectIdentifierVisitor()).getValue()); return list; } ));
    for (String formal : formals)
    { bodyEnvironment.addLocalVariable(formal);
    }

    List<Statement> body = first.getCdr().accept
      (new ASTListFoldVisitor<List<Statement>>(new ArrayList<>(),
        (list, ast) -> { list.add(ast.accept(new ASTConvertVisitor(bodyEnvironment))); return list; } ));
    if (body.isEmpty())
    { throw new SyntaxErrorException("Empty lambda body", operator.getSourceInfo());
    }
    BeginStatement bodyStatement = new BeginStatement(body);

    List<IdentifierStatement> closureVariables = new ArrayList<>();
    // Look up in the current environment, as these will be used to get the be variable value
    // to save them in the created function's closure
    for (String variable : bodyStatement.getFreeIdentifiers())
    { if (bodyEnvironment.getOrGlobal(variable).shouldSaveToClosure()) // Note the use of bodyEnvironment here and environment below
      { closureVariables.add((IdentifierStatement) environment.getOrGlobal(variable).toStatement(operator.getSourceInfo()));
      }
    }

    return new LambdaStatement(formals, closureVariables, bodyStatement, new ConsValue(operator, operands, operator.getSourceInfo()).toJavaValue().toString());
  }
}
