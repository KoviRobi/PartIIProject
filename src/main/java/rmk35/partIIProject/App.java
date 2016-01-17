package rmk35.partIIProject;

import rmk35.partIIProject.frontend.SchemeParser;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.Interconnect;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.JavaBytecodeGenerator;

import java.util.List;
import java.io.IOException;

public class App
{ public static void main(String[] arguments) throws IOException
  { if (arguments.length != 1)
    { System.out.println("Expecting only one argument, the file name to parse.");
      System.exit(1);
    }

    String fileName = arguments[0];
    List<AST> parsedFile = SchemeParser.parseFile(fileName);
    List<Statement> statements = Interconnect.ASTsToStatements(parsedFile);
    JavaBytecodeGenerator.generateOutput(fileName, statements);
  }
}
