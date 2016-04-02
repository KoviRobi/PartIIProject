package rmk35.partIIProject;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.frontend.SchemeParser;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.LibraryOrProgramme;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.statements.Statement;

import java.util.List;
import java.io.IOException;

public class Compiler
{ public static void main(String[] arguments) throws Exception, IOException
  { if (arguments.length != 1)
    { System.out.println("Expecting only one argument, the file name to parse.");
      System.exit(1);
    }

    String fileName = arguments[0];
    String outputName = removeExtension(fileName);
    new Compiler(fileName, outputName);
  }

  static String removeExtension(String fileName)
  { int lastIndex = fileName.lastIndexOf('.');
    if (lastIndex == -1)
    { return fileName;
    } else
    { return fileName.substring(0, lastIndex);
    }
  }

  public Compiler(String fileName, String outputName) throws Exception, IOException
  { MainClass mainClass = new MainClass(outputName);
    List<RuntimeValue> parsedFile = SchemeParser.parseFile(fileName);
    EnvironmentValue environment = new EnvironmentValue(/* mutable */ true);
    Statement programme = new LibraryOrProgramme(environment).compile(parsedFile);
    // Mutates mainClass
    programme.generateOutput(mainClass, mainClass.getMainInnerClass(), mainClass.getPrimaryMethod());
    mainClass.saveToDisk(); // For debugging purposes
    mainClass.assembleToDisk();
  }
}
