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

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.InnerClass;
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
  public Statement applicate(EnvironmentValue environment, OutputClass outputClass, MainClass mainClass, RuntimeValue operator, RuntimeValue operands)
  { ConsValue first = operands.accept(new ASTExpectConsVisitor());
    EnvironmentValue bodyEnvironment = environment.subEnvironment(); // Next: increment subenvironment's number

    String innerClassName = mainClass.uniqueID() + "$Lambda"; // Using main class' unique ID as that way all files definitely have different names
    String comment = new ConsValue(operator, operands, operator.getSourceInfo()).toJavaValue().toString();

    List<Binding> formals = first.getCar().accept(new ASTImproperListFoldVisitor<List<Binding>>(new ArrayList<Binding>(),
      (List<Binding> list, RuntimeValue ast) ->
      { list.add
          (bodyEnvironment.addLocalVariable
            (innerClassName,
            ast.accept(new ASTExpectIdentifierVisitor()).getValue()));
        return list;
      } ));

    InnerClass innerClass = new InnerClass(innerClassName, formals, mainClass, comment);

    List<Statement> body = first.getCdr().accept
      (new ASTListFoldVisitor<List<Statement>>(new ArrayList<>(),
        (list, ast) -> { list.add(ast.accept(new ASTConvertVisitor(bodyEnvironment, innerClass, mainClass))); return list; } ));
    if (body.isEmpty())
    { throw new SyntaxErrorException("Empty lambda body", operator.getSourceInfo());
    }
    BeginStatement bodyStatement = new BeginStatement(body);

    return new LambdaStatement(innerClass, bodyStatement, comment);
  }
}
