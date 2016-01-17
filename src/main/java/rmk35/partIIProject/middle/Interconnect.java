package rmk35.partIIProject.middle;

import rmk35.partIIProject.frontend.SchemeParser;

import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.bindings.SyntaxBinding;

import rmk35.partIIProject.backend.runtimeValues.*;
import rmk35.partIIProject.backend.statements.*;

import java.util.List;
import java.util.ArrayList;

public class Interconnect
{ static Environment InitialEnvironment;
  static Binding LambdaSyntaxBinding;
  static Binding LetSyntaxBinding;
  static
  { // ToDo: initialize Bindings
    LambdaSyntaxBinding = new SyntaxBinding();
    LetSyntaxBinding = new SyntaxBinding();
    InitialEnvironment = new Environment();
    InitialEnvironment.addBinding("lambda", LambdaSyntaxBinding);
  }

  public static List<Statement> ASTsToStatements(List<AST> datum)
  { List<Statement> returnValue = new ArrayList<>(datum.size());
    ASTConvertVisitor visitor = new ASTConvertVisitor(InitialEnvironment);

    for (AST ast : datum)
    { // Holds state of the environment
      returnValue.add(ast.accept(visitor));
    }

    return returnValue;
  }

  public static void main(String[] arguments)
  { System.out.println
    (ASTsToStatements
      (SchemeParser.parseString(arguments[0])) );
  }
}
