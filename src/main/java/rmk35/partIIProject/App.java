package rmk35.partIIProject;

import rmk35.partIIProject.frontend.SchemeParser;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.Interconnect;
import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.bindings.LambdaSyntaxBinding;
import rmk35.partIIProject.middle.bindings.LetSyntaxBinding;
import rmk35.partIIProject.middle.bindings.JavaCallBinding;
import rmk35.partIIProject.middle.bindings.JavaClassBinding;
import rmk35.partIIProject.middle.bindings.JavaFieldBinding;
import rmk35.partIIProject.middle.bindings.JavaMethodBinding;
import rmk35.partIIProject.middle.bindings.JavaStaticFieldBinding;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.JavaByteCodeGenerator;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;

import java.util.List;
import java.io.IOException;

public class App
{ static Interconnect interconnect;
  static
  { Environment initialEnvironment = new Environment();
    initialEnvironment.addBinding("lambda", new LambdaSyntaxBinding());
    initialEnvironment.addBinding("let-syntax", new LetSyntaxBinding());
    initialEnvironment.addBinding("java", new JavaCallBinding());
    initialEnvironment.addBinding("class", new JavaClassBinding());
    initialEnvironment.addBinding("field", new JavaFieldBinding());
    initialEnvironment.addBinding("method", new JavaMethodBinding());
    initialEnvironment.addBinding("static-field", new JavaStaticFieldBinding());
    interconnect = new Interconnect(initialEnvironment);
  }

  public static void main(String[] arguments) throws IOException
  { if (arguments.length != 1)
    { System.out.println("Expecting only one argument, the file name to parse.");
      System.exit(1);
    }

    String fileName = arguments[0];
    List<AST> parsedFile = SchemeParser.parseFile(fileName);
    String outputName = removeExtension(fileName);
    MainClass mainClass = new MainClass(outputName);
    List<Statement> statements = interconnect.ASTsToStatements(parsedFile);
    // Mutates mainClass
    JavaByteCodeGenerator.generateOutput(mainClass, statements);
    mainClass.saveToDisk();
  }

  public static String removeExtension(String fileName)
  { int lastIndex = fileName.lastIndexOf('.');
    if (lastIndex == -1)
    { return fileName;
    } else
    { return fileName.substring(0, lastIndex);
    }
  }
}
