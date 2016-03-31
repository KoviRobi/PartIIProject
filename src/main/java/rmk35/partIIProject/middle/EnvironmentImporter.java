package rmk35.partIIProject.middle;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.EnvironmentValue;
import rmk35.partIIProject.runtime.UnspecifiedValue;

import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.RuntimeValueStatement;
import rmk35.partIIProject.backend.statements.InstructionStatement;
import rmk35.partIIProject.backend.statements.DefineStatement;
import rmk35.partIIProject.backend.statements.SequenceStatement;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.PopInstruction;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class EnvironmentImporter
{ EnvironmentValue environment;

  public EnvironmentImporter(EnvironmentValue environment)
  { this.environment = environment;
  }

  public Statement importEnvironment(ASTMatcher datum)
  { if (datum.get("import-set") == null) throw new InternalCompilerException("Expected a matcher to have matched \"import-set\"");
    List<Statement> sequenceStatement = new ArrayList<>();
    datum.get("import-set").forEach(importSet ->
    { EnvironmentValue foreignEnvironment = getEnvironment(importSet);
      sequenceStatement.add(new RuntimeValueStatement(foreignEnvironment));
      for (Map.Entry<String, Binding> binding : foreignEnvironment.entrySet())
      { if (binding.getValue().runtime())
        { sequenceStatement.add(new InstructionStatement(new DupInstruction())); // Invariant, Environment (to clone from) on stack
          sequenceStatement.add(new DefineStatement(environment.addGlobalVariable(binding.getKey()).toStatement(importSet.getSourceInfo()), binding.getValue().toStatement(importSet.getSourceInfo())));
          sequenceStatement.add(new InstructionStatement(new PopInstruction())); // Pop result of define
        } else
        { environment.addBinding(binding.getKey(), binding.getValue());
        }
      }
      sequenceStatement.add(new InstructionStatement(new PopInstruction()));
    });
    sequenceStatement.add(new RuntimeValueStatement(new UnspecifiedValue()));
    return new SequenceStatement(sequenceStatement); // Pop environment
  }

  public EnvironmentValue getEnvironment(RuntimeValue importSet)
  { // ToDo: At the moment, we only support simple library names
    // ToDo: This way only, except and so on are reserved
    // ToDo: At the moment this is not a problem as they appear before
    // ToDo: definitions
    ASTMatcher only = new ASTMatcher("(only import-set identifier ...)", importSet, "only");
    if (only.matched()) return handleOnly(only);

    ASTMatcher except = new ASTMatcher("(except import-set identifier ...)", importSet, "except");
    if (except.matched()) handleExcept(except);

    ASTMatcher prefix = new ASTMatcher("(prefix import-set identifier)", importSet, "prefix");
    if (prefix.matched()) handlePrefix(prefix);

    ASTMatcher rename = new ASTMatcher("(rename import-set (from to) ...)", importSet, "rename");
    if (rename.matched()) handleRename(rename);

    ASTMatcher libraryName = new ASTMatcher("(library-name-part ...)", importSet);
    if (libraryName.matched())
    { List<String> nameParts = new ArrayList<>();
      if (libraryName.get("library-name-part") == null) throw new SyntaxErrorException("Empty library name", importSet.getSourceInfo());
      libraryName.get("library-name-part").forEach(name ->
        nameParts.add(name.accept(new ASTExpectIdentifierVisitor()).getValue()));
      String className = String.join(".", nameParts);
      try
      { return (EnvironmentValue) Class.forName(className).newInstance();
      } catch (ClassNotFoundException e)
      { throw new SyntaxErrorException("Library can't be loaded: " + className + ", note that as we use the Java class loader, we expect it to be compiled and in a directory structure matching its name up to the penultimate part", importSet.getSourceInfo());
      } catch (InstantiationException | IllegalAccessException e)
      { throw new RuntimeException(e);
      }
    }
    throw new SyntaxErrorException("Bad library import: " + importSet, importSet.getSourceInfo());
  }

  public EnvironmentValue handleOnly(ASTMatcher only)
  { throw new UnsupportedOperationException("Only imports are not yet supported");
  }

  public EnvironmentValue handleExcept(ASTMatcher except)
  { throw new UnsupportedOperationException("Except imports are not yet supported");
  }

  public EnvironmentValue handlePrefix(ASTMatcher prefix)
  { throw new UnsupportedOperationException("Prefix imports are not yet supported");
  }

  public EnvironmentValue handleRename(ASTMatcher rename)
  { throw new UnsupportedOperationException("Rename imports are not yet supported");
  }
}
