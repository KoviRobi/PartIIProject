package rmk35.partIIProject.middle;

import rmk35.partIIProject.Compiler;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.astExpectVisitor.ASTListMapVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierOrIntegerVisitor;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.LibraryClass;
import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.BeginStatement;

import java.util.List;
import java.util.ArrayList;

public class LibraryOrProgramme
{ EnvironmentValue environment;
  MainClass mainClass;

  public LibraryOrProgramme(EnvironmentValue environment, MainClass mainClass)
  { this.environment = environment;
    this.mainClass = mainClass;
  }

  public Statement compile(List<RuntimeValue> data)
  { List<Statement> returnValue = new ArrayList<>(data.size());
    EnvironmentImporter importer = new EnvironmentImporter(environment);
    ASTConvertVisitor convertVisitor = new ASTConvertVisitor(environment, mainClass.getMainInnerClass(), mainClass);
    boolean finishedImporting = false;

    for (RuntimeValue datum : data)
    { ASTMatcher library = new ASTMatcher(Compiler.baseEnvironment, environment, "(define-library library-name library-declaration ...)", datum, "define-library");
      if (library.matched())
      { List<String> libraryName = library.transform("library-name").accept
          (new ASTListMapVisitor<>(new ASTExpectIdentifierOrIntegerVisitor()));
        LibraryClass libraryClass = new LibraryClass(libraryName);
        ASTLibraryVisitor libraryVisitor = new ASTLibraryVisitor(new EnvironmentValue(/* mutable */ true), libraryClass);
        for (RuntimeValue declaration : library.transform("(library-declaration ...)").accept(new ASTListMapVisitor<>(x -> x)))
        { declaration.accept(libraryVisitor);
        }
        mainClass.addLibrary(libraryClass);
        continue;
      }

      ASTMatcher importDeclaration = new ASTMatcher(Compiler.baseEnvironment, environment, "(import import-set ...)", datum, "import");
      if (! finishedImporting && importDeclaration.matched())
      { List<RuntimeValue> imports = new ArrayList<>();
        importDeclaration.get("import-set").forEach(value -> imports.add(value));
        importer.importEnvironment(imports);
      } else
      { finishedImporting = true;
        returnValue.add(datum.accept(convertVisitor));
      }
    }
    return new BeginStatement(returnValue);
  }
}