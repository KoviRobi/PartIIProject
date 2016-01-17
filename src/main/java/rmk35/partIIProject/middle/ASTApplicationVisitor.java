package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
import rmk35.partIIProject.frontend.AST.SchemeList;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;

import rmk35.partIIProject.middle.bindings.Binding;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.ApplicationStatement;
import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.LambdaStatement;

import lombok.Value;

import java.util.List;
import java.util.ArrayList;

@Value
public class ASTApplicationVisitor extends ASTVisitor<Statement>
{ Environment environment;
  List<AST> application;

  public ASTApplicationVisitor(Environment environment, List<AST> application)
  { this.environment = environment;
    this.application = application;
  }

  public Statement visit(SchemeIdentifier identifier) throws SyntaxErrorException
  { Binding head = environment.lookUp(identifier.getData());
    if (head == Interconnect.LambdaSyntaxBinding)
    { if (application.size() < 3)
      { throw new SyntaxErrorException("Too few elements to make a lambda.", identifier.file(), identifier.line(), identifier.character());
      } else
      { Environment bodyEnvironment = new Environment(environment);
        List<String> formals = application.get(1).accept(new ASTLambdaFormalsVisitor());
        bodyEnvironment.addLocalVariables(formals);
        Statement body = application.get(2).accept
          (new ASTConvertVisitor(bodyEnvironment)); // ToDo: implicit begin

        List<IdentifierStatement> closureVariables = new ArrayList<>();
        // Look up in the current environment, as these will be used to get the be variable value
        // to save them in the created function's closure
        for (String variable : body.getFreeIdentifiers())
        { if (bodyEnvironment.lookUp(variable).shouldSaveToClosure()) // Note the use of bodyEnvironment here and environment below
          { closureVariables.add((IdentifierStatement)environment.lookUpAsStatement(variable));
          }
        }

        return new LambdaStatement(formals, closureVariables, body);
      }
    } else if (head == Interconnect.LetSyntaxBinding)
    { // Add a macro binding to the environment
      // NEXT: environment.addBinding();
      return null;
    } else
    { // Actual application, return an application statement
      return new ApplicationStatement
      (application.parallelStream()
                  .map(ast -> ast.accept(new ASTConvertVisitor(environment)))
                  .toArray(Statement[]::new));
    }
  }

  // FIXME: These are not internal exceptions, but are bad code
  public Statement visit(SchemeList list) throws SyntaxErrorException
  { throw new SyntaxErrorException("Don't know how to apply a list as an operand", list.file(), list.line(), list.character());
  }

  public Statement visit(SchemeLiteral abbreviation) throws SyntaxErrorException
  { throw new SyntaxErrorException("Don't know how to apply a value as an operand", abbreviation.file(), abbreviation.line(), abbreviation.character());
  }

  public Statement visit(SchemeLabelReference reference) throws SyntaxErrorException
  { throw new SyntaxErrorException("Don't know how to apply a label reference", reference.file(), reference.line(), reference.character());
  }

  public Statement visit(SchemeLabelledData data) throws SyntaxErrorException
  { throw new SyntaxErrorException("Don't know how to handle non-literal labelled data", data.file(), data.line(), data.character());
  }
}