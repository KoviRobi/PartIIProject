package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.backend.statements.Statement;

import java.util.List;
import java.util.ArrayList;

public class LibraryOrProgramme
{ public List<Statement> compile(List<RuntimeValue> data)
  { List<Statement> returnValue = new ArrayList<>(data.size());
    EnvironmentValue environment = new EnvironmentValue(/* mutable */ true);
    EnvironmentImporter importer = new EnvironmentImporter(environment);
    ASTConvertVisitor convertVisitor = new ASTConvertVisitor(environment);
    boolean finishedImporting = false;

    for (RuntimeValue datum : data)
    { ASTMatcher library = new ASTMatcher("(define-library library-name library-declaration ...)", datum, "define-library");
      if (library.matched())
      { if (! returnValue.isEmpty())
        { throw new SyntaxErrorException("Was not expecting library definition in a programme", datum.getSourceInfo());
        }
        if (data.size() > 1)
        { throw new SyntaxErrorException("Was only expect a library definition, nothing more", data.get(1).getSourceInfo());
        }
        throw new UnsupportedOperationException("We don't support library definitions yet");
      }

      ASTMatcher importDeclaration = new ASTMatcher("(import import-set ...)", datum, "import");
      if (! finishedImporting && importDeclaration.matched())
      { returnValue.add(importer.importEnvironment(importDeclaration));
      } else
      { finishedImporting = true;
        returnValue.add(datum.accept(convertVisitor));
      }
    }
    return returnValue;
  }
}