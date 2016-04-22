package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;
import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.SelfquotingValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.ApplicationStatement;

import java.util.List;
import java.util.ArrayList;

import lombok.Value;

@Value
public class ASTApplicationVisitor extends ASTVisitor<Statement>
{ EnvironmentValue environment;
  OutputClass outputClass;
  MainClass mainClass;
  RuntimeValue arguments;

  public ASTApplicationVisitor(EnvironmentValue environment, OutputClass outputClass, MainClass mainClass, RuntimeValue arguments)
  { this.environment = environment;
    this.outputClass = outputClass;
    this.mainClass = mainClass;
    this.arguments = arguments;
  }

  @Override
  public Statement visit(ConsValue consCell) throws SyntaxErrorException
  { return new ApplicationStatement(consCell.accept(new ASTConvertVisitor(environment, outputClass, mainClass)),
      arguments.accept(new ASTListFoldVisitor<List<Statement>>(new ArrayList<>(),
        (list, ast) -> { list.add(ast.accept(new ASTConvertVisitor(environment, outputClass, mainClass))); return list; } )));
  }

  @Override
  public Statement visit(IdentifierValue identifier) throws SyntaxErrorException
  { return environment.getOrGlobal(mainClass, identifier.getValue()).applicate(environment, outputClass, mainClass, identifier, arguments);
  }

  @Override
  public Statement visit(NullValue nil) throws SyntaxErrorException
  { throw new SyntaxErrorException("Don't know how to apply nil", nil.getSourceInfo());
  }

  @Override
  public Statement visit(SelfquotingValue object) throws SyntaxErrorException
  { throw new SyntaxErrorException("Don't know how to apply a constant as an operand", object.getSourceInfo());
  }
}
