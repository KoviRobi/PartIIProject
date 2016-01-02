package rmk35.partIIProject.middle;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.frontend.AST.SchemeObject;
import rmk35.partIIProject.frontend.AST.SchemeList;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;

import rmk35.partIIProject.middle.bindings.Binding;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.ApplicationStatement;

import lombok.Value;

import java.util.List;

@Value
public class ASTApplicationVisitor implements ASTVisitor
{ Environment environment;
  List<AST> application;

  public Statement visit(SchemeIdentifier identifier)
  { Binding head = environment.lookUp(identifier.getData());
    if (head == Interconnect.LambdaSyntaxBinding)
    { // Use ASTConvertVisitor on the lambda body
      return null;
    } else if (head == Interconnect.LetSyntaxBinding)
    { // Add a macro binding to the environment
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