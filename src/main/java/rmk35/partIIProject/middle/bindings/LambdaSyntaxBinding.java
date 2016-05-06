package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.Compiler;
import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.EnvironmentValue;
import rmk35.partIIProject.runtime.IdentifierValue;

import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.ASTMatcher;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTImproperListFoldVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListMapVisitor;

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
  { EnvironmentValue bodyEnvironment = environment.subEnvironment();

    String innerClassName = mainClass.uniqueID() + "$Lambda"; // Using main class' unique ID as that way all files definitely have different names
    String comment = new ConsValue(operator, operands, operator.getSourceInfo()).writeString();

    ASTMatcher lambda = new ASTMatcher(Compiler.baseEnvironment, environment, "((arguments ... . final) body ...)", operands);
    if (!lambda.matched())
    { throw new SyntaxErrorException("Wrong syntax for lambda: " + comment, operator.getSourceInfo());
    }

    List<IdentifierValue> formals = lambda.transform("(arguments ...)").accept(new ASTListMapVisitor<>
      (new ASTExpectIdentifierVisitor()));
    IdentifierValue lastFormal;
    if (lambda.transform("final") instanceof NullValue)
    { lastFormal = null;
    } else
    { lastFormal = lambda.transform("final").accept(new ASTExpectIdentifierVisitor());
    }

    InnerClass innerClass = new InnerClass(mainClass.getPackage(), innerClassName, bodyEnvironment, formals, lastFormal, mainClass, comment);

    ASTVisitor<Statement> bodyVisitor = new ASTConvertVisitor(bodyEnvironment, innerClass, mainClass);
    List<Statement> body = lambda.transform("(body ...)").accept(new ASTListMapVisitor<>(bodyVisitor));
    BeginStatement bodyStatement = new BeginStatement(body);

    return new LambdaStatement(innerClass, bodyStatement, comment);
  }
}
