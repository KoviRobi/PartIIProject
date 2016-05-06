package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.SelfquotingValue;
import rmk35.partIIProject.runtime.EnvironmentValue;
import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.middle.astExpectVisitor.ASTListMapVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;

import rmk35.partIIProject.backend.LibraryClass;
import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.BeginStatement;

import java.util.List;
import java.util.ArrayList;

public class ASTLibraryVisitor extends ASTVisitor<Void>
{ EnvironmentValue environment; /* STATE */
  LibraryClass libraryClass;
  EnvironmentImporter importer;

  public ASTLibraryVisitor(EnvironmentValue environment, LibraryClass libraryClass)
  { this.environment =  environment;
    this.libraryClass = libraryClass;
    this.importer = new EnvironmentImporter(environment);
  }

  @Override
  public Void visit(RuntimeValue value)
  { throw new SyntaxErrorException("I was expecting a library declaration, got: " + value.toString(), value.getSourceInfo());
  }

  @Override
  public Void visit(ConsValue consCell)
  { ASTMatcher export = new ASTMatcher(environment, environment, "(export identifier ...)", consCell, "export");
    if (export.matched()) // ToDo: rename
    { export.get("identifier").forEach(value -> libraryClass.addLibraryExport(value.accept(new ASTExpectIdentifierVisitor()).getValue()));
      return null;
    }

    ASTMatcher importDeclaration = new ASTMatcher(environment, environment, "(import import-set ...)", consCell, "import");
    if (importDeclaration.matched())
    { List<RuntimeValue> imports = new ArrayList<>();
      importDeclaration.get("import-set").forEach(value -> imports.add(value));
      importer.importEnvironment(imports);
      return null;
    }

    ASTMatcher begin = new ASTMatcher(environment, environment, "(begin body ...)", consCell, "begin");
    if (begin.matched())
    { new BeginStatement(begin.transform("(body ...)")
      .accept(new ASTListMapVisitor<>(new ASTConvertVisitor(environment, libraryClass, libraryClass))))
      .generateOutput(libraryClass, libraryClass, libraryClass.getPrimaryMethod());
      return null;
    }

    // ToDo: include and cond-expand

    throw new SyntaxErrorException("I was expecting one of export, import, begin, include, include-ci, include-library-declarations or cond-expand, got: " + consCell.toString(),  consCell.getSourceInfo());
  }
}
