package rmk35.partIIProject.middle;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.frontend.AST.SchemeObject;
import rmk35.partIIProject.frontend.AST.SchemeList;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;

import rmk35.partIIProject.middle.bindings.Binding;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.ApplicationStatement;
import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.LambdaStatement;

import lombok.Value;

import java.util.List;
import java.util.ArrayList;

@Value
public class ASTApplicationVisitor implements ASTVisitor
{ Environment environment;
  List<AST> application;

  public ASTApplicationVisitor(Environment environment, List<AST> application)
  { this.environment = environment;
    this.application = application;
  }

  public Statement visit(SchemeIdentifier identifier)
  { Binding head = environment.lookUp(identifier.getData());
    if (head == Interconnect.LambdaSyntaxBinding)
    { Environment bodyEnvironment = new Environment(environment);
      List<String> formals =  null; // NEXT: Another visitor perhaps make visitors parameterized?
      bodyEnvironment.addLocalVariables(formals);
      Statement body = application.get(3).accept
        (new ASTConvertVisitor(new Environment(environment))); // ToDo: implicit begin

      List<IdentifierStatement> closureVariables = new ArrayList();
      // Look up in the current environment, as these will be used to get the be variable value
      // to save them in the created function's closure
      // NEXT FIXME: What happens if variable looks up to be a SyntaxBinding?
      // SOLN FIXME:: Only need variables that either have a local binding, or are closure variables, as only need to copy them
      for (IdentifierStatement variable : body.getFreeVariables())
      { closureVariables.add(environment.lookUpAsStatement(variable));
      }

      return new LambdaStatement(formals, closureVariables, body);
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
  public Statement visit(SchemeList list)
  { throw new InternalCompilerException("Don't know how to apply a list as an operand");
  }

  public Statement visit(SchemeObject abbreviation)
  { throw new InternalCompilerException("Don't know how to apply a value as an operand");
  }

  public Statement visit(SchemeLabelReference reference)
  { throw new InternalCompilerException("Don't know how to apply a list as an operand");
  }
}