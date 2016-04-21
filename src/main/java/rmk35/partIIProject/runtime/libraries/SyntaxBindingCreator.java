package rmk35.partIIProject.runtime.libraries;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.frontend.SchemeParser;

import rmk35.partIIProject.middle.bindings.SyntaxBinding;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class SyntaxBindingCreator
{ public static SyntaxBinding create(String... patternsAndTemplates)
  { if (patternsAndTemplates.length % 2 != 0)
      throw new InternalCompilerException("Not an even number of patterns and templates");
    EnvironmentValue environment = new EnvironmentValue(true);
    Collection<String> literals = new ArrayList<>();
    List<Pair<RuntimeValue, RuntimeValue>> parsedPatternsAndTemplates = new ArrayList<>();
    for (int i = 0; i < patternsAndTemplates.length; i++)
    { parsedPatternsAndTemplates.add(new Pair<>(SchemeParser.read(patternsAndTemplates[i]),
        SchemeParser.read(patternsAndTemplates[i++])));
    }
    return new SyntaxBinding(environment, literals, parsedPatternsAndTemplates, "...");
  }
}