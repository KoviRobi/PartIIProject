package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.Compiler;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.ASTMatcher;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectNilVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListMapVisitor;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.InnerClass;
import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.GlobalIdentifierStatement;
import rmk35.partIIProject.backend.statements.DefineStatement;
import rmk35.partIIProject.backend.statements.BeginStatement;
import rmk35.partIIProject.backend.statements.LambdaStatement;

import java.util.List;
import java.util.ArrayList;

import lombok.ToString;

@ToString
public class DefineBinding extends SintacticBinding
{ @Override
  public Statement applicate(EnvironmentValue environment, OutputClass outputClass, MainClass mainClass, RuntimeValue operator, RuntimeValue operands)
  { ASTMatcher functionDefine = new ASTMatcher(Compiler.baseEnvironment, environment, "((name arguments ... . final) body ...)", operands);
    if (functionDefine.matched())
    { String name = functionDefine.transform("name").accept(new ASTExpectIdentifierVisitor()).getValue();
      Binding variableBinding = environment.getOrNull(name);
      if (variableBinding == null || ! (variableBinding instanceof VariableBinding))
      { environment.addGlobalVariable(mainClass, name);
      }

      EnvironmentValue bodyEnvironment = environment.subEnvironment();

      List<Binding> formals = functionDefine.transform("(arguments ...)").accept(new ASTListMapVisitor<>
        (ast -> bodyEnvironment.addLocalVariable(name, ast.accept(new ASTExpectIdentifierVisitor()).getValue())));
      Binding lastFormal;
      if (functionDefine.transform("final") instanceof NullValue)
      { lastFormal = null;
      } else
      { lastFormal = bodyEnvironment.addLocalVariable(name, functionDefine.transform("final").accept(new ASTExpectIdentifierVisitor()).getValue());
      }
      String comment = new ConsValue(operator, operands, operator.getSourceInfo()).writeString();
      InnerClass innerClass = new InnerClass(name, formals, lastFormal, mainClass, comment);

      ASTVisitor<Statement> convertVisitor = new ASTConvertVisitor(bodyEnvironment, innerClass, mainClass);
      List<Statement> body = functionDefine.transform("(body ...)").accept(new ASTListMapVisitor<>(convertVisitor));
      if (body.isEmpty())
      { throw new SyntaxErrorException("Empty lambda body", operator.getSourceInfo());
      }
      BeginStatement bodyStatement = new BeginStatement(body);

      IdentifierStatement variableStatement = (IdentifierStatement) environment.getOrGlobal(mainClass, name).toStatement(operator.getSourceInfo());
      return new DefineStatement(variableStatement, new LambdaStatement(innerClass, bodyStatement, comment));
    }

    // (define foo bar)
    ConsValue first = operands.accept(new ASTExpectConsVisitor());
    String variable = first.getCar().accept(new ASTExpectIdentifierVisitor()).getValue();
    Binding variableBinding = environment.getOrNull(variable);
    ConsValue second = first.getCdr().accept(new ASTExpectConsVisitor());
    Statement expression = second.getCar().accept(new ASTConvertVisitor(environment, outputClass, mainClass));
    second.getCdr().accept(new ASTExpectNilVisitor());

    if (variableBinding == null || ! (variableBinding instanceof VariableBinding))
    { environment.addGlobalVariable(mainClass, variable);
    }

    IdentifierStatement variableStatement = (IdentifierStatement) environment.getOrGlobal(mainClass, variable).toStatement(operator.getSourceInfo());
    return new DefineStatement(variableStatement, expression);
  }
}
