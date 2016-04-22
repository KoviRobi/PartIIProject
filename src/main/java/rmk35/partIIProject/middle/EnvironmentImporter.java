package rmk35.partIIProject.middle;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.EnvironmentValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.UnspecifiedValue;
import rmk35.partIIProject.runtime.libraries.GhostReflectiveEnvironment;

import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTPairMapVisitor;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.RuntimeValueStatement;
import rmk35.partIIProject.backend.statements.InstructionStatement;
import rmk35.partIIProject.backend.statements.DefineStatement;
import rmk35.partIIProject.backend.statements.SequenceStatement;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.PopInstruction;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class EnvironmentImporter
{ EnvironmentValue environment;

  public EnvironmentImporter(EnvironmentValue environment)
  { this.environment = environment;
  }

  public void importEnvironment(List<RuntimeValue> importSets)
  { for (RuntimeValue importSet : importSets)
    { EnvironmentValue foreignEnvironment = getEnvironment(importSet);
      for (Map.Entry<String, Binding> binding : foreignEnvironment.entrySet())
      { environment.addBinding(binding.getKey(), binding.getValue());
      }
    }
  }

  public EnvironmentValue getEnvironment(RuntimeValue importSet)
  { // ToDo: This way only, except and so on are reserved
    // ToDo: At the moment this is not a problem as they appear before
    // ToDo: definitions
    ASTMatcher only = new ASTMatcher("(only import-set identifier ...)", importSet, "only");
    if (only.matched()) return handleOnly(only);

    ASTMatcher except = new ASTMatcher("(except import-set identifier ...)", importSet, "except");
    if (except.matched()) return handleExcept(except);

    ASTMatcher prefix = new ASTMatcher("(prefix import-set identifier)", importSet, "prefix");
    if (prefix.matched()) return handlePrefix(prefix);

    ASTMatcher rename = new ASTMatcher("(rename import-set (from to) ...)", importSet, "rename");
    if (rename.matched()) return handleRename(rename);

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

  EnvironmentValue handleOnly(ASTMatcher only)
  { EnvironmentValue importSet = getEnvironment(only.transform("import-set"));
    EnvironmentValue returnValue = new GhostReflectiveEnvironment(importSet);
    returnValue.setMutable(true);
    Set<String> onlyThese = new HashSet<>();
    only.get("identifier").forEach(value -> onlyThese.add(value.accept(new ASTExpectIdentifierVisitor()).getValue()));
    for (Map.Entry<String, Binding> binding : importSet.entrySet())
    { if (onlyThese.contains(binding.getKey()))
      { returnValue.addBinding(binding.getKey(), binding.getValue());
      }
    }
    returnValue.setMutable(false);
    return returnValue;
  }

  EnvironmentValue handleExcept(ASTMatcher except)
  { EnvironmentValue importSet = getEnvironment(except.transform("import-set"));
    EnvironmentValue returnValue = new GhostReflectiveEnvironment(importSet);
    returnValue.setMutable(true);
    Set<String> exceptThese = new HashSet<>();
    except.get("identifier").forEach(value -> exceptThese.add(value.accept(new ASTExpectIdentifierVisitor()).getValue()));
    for (Map.Entry<String, Binding> binding : importSet.entrySet())
    { if (! exceptThese.contains(binding.getKey()))
      { returnValue.addBinding(binding.getKey(), binding.getValue());
      }
    }
    returnValue.setMutable(false);
    return returnValue;
  }

  EnvironmentValue handlePrefix(ASTMatcher prefix)
  { EnvironmentValue importSet = getEnvironment(prefix.transform("import-set"));
    EnvironmentValue returnValue = new GhostReflectiveEnvironment(importSet);
    returnValue.setMutable(true);
    String prefixString = prefix.transform("identifier").accept(new ASTExpectIdentifierVisitor()).getValue();
    for (Map.Entry<String, Binding> binding : importSet.entrySet())
    { returnValue.addBinding(prefixString + binding.getKey(), binding.getValue());
    }
    returnValue.setMutable(false);
    return returnValue;
  }

  EnvironmentValue handleRename(ASTMatcher rename)
  { EnvironmentValue importSet = getEnvironment(rename.transform("import-set"));
    EnvironmentValue returnValue = new GhostReflectiveEnvironment(importSet);
    returnValue.setMutable(true);
    Map<String, String> renameThese = new HashMap<>();
    rename.transform("((from to) ...)")
      .accept(new ASTListFoldVisitor<>(null /* don't care */,
      (acc, pairValue) ->
      { Pair<String, String> pair = pairValue.accept(new ASTPairMapVisitor<>
          (from -> from.accept(new ASTExpectIdentifierVisitor()).getValue(),
           to -> to.accept(new ASTExpectIdentifierVisitor()).getValue()));
        renameThese.put(pair.getFirst(), pair.getSecond());
        return null;
      }));
    for (Map.Entry<String, Binding> binding : importSet.entrySet())
    { if (renameThese.containsKey(binding.getKey()))
      { returnValue.addBinding(renameThese.get(binding.getKey()), binding.getValue());
      } else
      { returnValue.addBinding(binding.getKey(), binding.getValue());
      }
    }
    returnValue.setMutable(false);
    return returnValue;
  }
}
