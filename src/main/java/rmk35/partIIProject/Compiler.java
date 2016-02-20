package rmk35.partIIProject;

import rmk35.partIIProject.frontend.SchemeParser;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.Interconnect;
import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.bindings.LambdaSyntaxBinding;
import rmk35.partIIProject.middle.bindings.IfBinding;
import rmk35.partIIProject.middle.bindings.DefineBinding;
import rmk35.partIIProject.middle.bindings.SetBinding;
import rmk35.partIIProject.middle.bindings.LetBinding;
import rmk35.partIIProject.middle.bindings.QuoteBinding;
import rmk35.partIIProject.middle.bindings.DefineSyntaxBinding;
import rmk35.partIIProject.middle.bindings.LetSyntaxBinding;
import rmk35.partIIProject.middle.bindings.SyntaxRulesBinding;
import rmk35.partIIProject.middle.bindings.JavaCallBinding;
import rmk35.partIIProject.middle.bindings.JavaClassBinding;
import rmk35.partIIProject.middle.bindings.JavaFieldBinding;
import rmk35.partIIProject.middle.bindings.JavaMethodBinding;
import rmk35.partIIProject.middle.bindings.JavaStaticFieldBinding;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.RaiseLambda;
import rmk35.partIIProject.runtime.WithExceptionHandlerLambda;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.JavaByteCodeGenerator;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;

import java.util.List;
import java.io.IOException;

public class Compiler
{ Interconnect interconnect;
  Environment initialEnvironment;
  MainClass mainClass;

  public static void main(String[] arguments) throws IOException
  { if (arguments.length != 1)
    { System.out.println("Expecting only one argument, the file name to parse.");
      System.exit(1);
    }

    String fileName = arguments[0];
    String outputName = removeExtension(fileName);
    new Compiler(fileName, outputName);
  }

  public static String removeExtension(String fileName)
  { int lastIndex = fileName.lastIndexOf('.');
    if (lastIndex == -1)
    { return fileName;
    } else
    { return fileName.substring(0, lastIndex);
    }
  }

  public Compiler(String fileName, String outputName) throws IOException
  { initialEnvironment = new Environment();
    // Syntactic bindings (these get transformed before main class)
    initialEnvironment.addBinding("lambda", new LambdaSyntaxBinding());
    initialEnvironment.addBinding("if", new IfBinding());
    initialEnvironment.addBinding("define", new DefineBinding());
    initialEnvironment.addBinding("set!", new SetBinding());
    initialEnvironment.addBinding("let", new LetBinding());
    initialEnvironment.addBinding("define-syntax", new DefineSyntaxBinding());
    initialEnvironment.addBinding("let-syntax", new LetSyntaxBinding());
    initialEnvironment.addBinding("syntax-rules", new SyntaxRulesBinding());
    initialEnvironment.addBinding("quote", new QuoteBinding());
    initialEnvironment.addBinding("java", new JavaCallBinding());
    initialEnvironment.addBinding("class", new JavaClassBinding());
    initialEnvironment.addBinding("field", new JavaFieldBinding());
    initialEnvironment.addBinding("method", new JavaMethodBinding());
    initialEnvironment.addBinding("static-field", new JavaStaticFieldBinding());

    interconnect = new Interconnect(initialEnvironment);
    MainClass mainClass = new MainClass(outputName);

    // Runtime bindings (they need to be initialized, hence adding them to main class)
    // Exceptions
    initialEnvironment.addGlobalVariable("raise");
    mainClass.addGlobalBinding("raise", RaiseLambda.class);
    initialEnvironment.addGlobalVariable("with-exception-handler");
    mainClass.addGlobalBinding("with-exception-handler", WithExceptionHandlerLambda.class);

    List<RuntimeValue> parsedFile = SchemeParser.parseFile(fileName);
    List<Statement> statements = interconnect.ASTsToStatements(parsedFile);
    // Mutates mainClass
    JavaByteCodeGenerator.generateOutput(mainClass, statements);
    mainClass.saveToDisk();
  }
}
