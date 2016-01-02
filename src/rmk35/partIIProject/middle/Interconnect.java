package rmk35.partIIProject.middle;

import rmk35.partIIProject.frontend.SchemeParser;

import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.bindings.SyntaxBinding;

import rmk35.partIIProject.backend.runtimeValues.*;
import rmk35.partIIProject.backend.statements.*;

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

  public static Statement ASTToStatement(AST syntax)
  { ASTVisitor visitor = new ASTConvertVisitor(InitialEnvironment);
    return syntax.accept(visitor);
  }

  public static void main(String[] arguments)
  { System.out.println
    (ASTToStatement
      (SchemeParser.parseString("(foo bar baz)").get(0)) );
  }
}
