package rmk35.partIIProject.middle;

import rmk35.partIIProject.frontend.SchemeParser;

import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.bindings.LambdaSyntaxBinding;
import rmk35.partIIProject.middle.bindings.LetSyntaxBinding;
import rmk35.partIIProject.middle.bindings.JavaCallBinding;
import rmk35.partIIProject.middle.bindings.JavaClassBinding;
import rmk35.partIIProject.middle.bindings.JavaFieldBinding;
import rmk35.partIIProject.middle.bindings.JavaMethodBinding;
import rmk35.partIIProject.middle.bindings.JavaStaticFieldBinding;

import rmk35.partIIProject.backend.runtimeValues.*;
import rmk35.partIIProject.backend.statements.*;

import java.util.List;
import java.util.ArrayList;

public class Interconnect
{ static Environment InitialEnvironment;
  static
  { // ToDo: initialize Bindings
    Binding LambdaSyntaxBinding = new LambdaSyntaxBinding();
    Binding LetSyntaxBinding = new LetSyntaxBinding();
    Binding JavaCallBinding = new JavaCallBinding();
    Binding JavaClassBinding = new JavaClassBinding();
    Binding JavaFieldBinding = new JavaFieldBinding();
    Binding JavaMethodBinding = new JavaMethodBinding();
    Binding JavaStaticFieldBinding = new JavaStaticFieldBinding();
    InitialEnvironment = new Environment();
    InitialEnvironment.addBinding("lambda", LambdaSyntaxBinding);
    InitialEnvironment.addBinding("let-syntax", LetSyntaxBinding);
    InitialEnvironment.addBinding("java", JavaCallBinding);
    InitialEnvironment.addBinding("class", JavaClassBinding);
    InitialEnvironment.addBinding("field", JavaFieldBinding);
    InitialEnvironment.addBinding("method", JavaMethodBinding);
    InitialEnvironment.addBinding("static-field", JavaStaticFieldBinding);
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
