package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.EnvironmentValue;
import rmk35.partIIProject.runtime.libraries.CarbonCopyEnvironment;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.astMacroMatchVisitor.ASTCompilePatternVisitor;
import rmk35.partIIProject.middle.astMacroMatchVisitor.ASTMatchVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.ASTMacroRewriteVisitor;

import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.statements.Statement;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Hashtable;

import lombok.ToString;

@ToString
public class SyntaxBinding extends SintacticBinding
{ EnvironmentValue definitionEnvironment;
  Collection<String> literals;
  List<Pair<ASTMatchVisitor, Pair<Collection<String>, RuntimeValue>>> patternsAndTemplates;
  String ellipsisString;

  public SyntaxBinding(EnvironmentValue definitionEnvironment, Collection<String> literals, List<Pair<RuntimeValue, RuntimeValue>> patternsAndTemplates, String ellipsisString)
  { this.ellipsisString = ellipsisString;
    this.definitionEnvironment = definitionEnvironment;
    this.literals = literals;
    this.patternsAndTemplates = new ArrayList<>(patternsAndTemplates.size());
    Binding oldEllipsisBinding = definitionEnvironment.getOrNull(ellipsisString);
    definitionEnvironment.addBinding(ellipsisString, new EllipsisBinding());
    for (Pair<RuntimeValue, RuntimeValue> pair : patternsAndTemplates)
    { Pair<ASTMatchVisitor, Collection<String>> compiledPattern = pair.getFirst().accept(new ASTCompilePatternVisitor(literals, definitionEnvironment));
      this.patternsAndTemplates.add(new Pair<>(compiledPattern.getFirst(), new Pair<>(compiledPattern.getSecond(), pair.getSecond())));
    }
    if (oldEllipsisBinding == null)
    { definitionEnvironment.removeBinding(ellipsisString);
    } else
    { definitionEnvironment.addBinding(ellipsisString, oldEllipsisBinding);
    }
  }

  @Override
  public Statement applicate(EnvironmentValue useEnvironment, OutputClass outputClass, MainClass mainClass, RuntimeValue operator, RuntimeValue operands)
  { // So that variables go up the correct number of parents
    EnvironmentValue subDefinitionEnvironment = definitionEnvironment;
    for (int i = definitionEnvironment.getLevel(); i < useEnvironment.getLevel(); i++)
    { subDefinitionEnvironment = subDefinitionEnvironment.subEnvironment();
    }
    // See Macros That Work, figure 3
    for (Pair<ASTMatchVisitor, Pair<Collection<String>, RuntimeValue>> pair : patternsAndTemplates)
    { pair.getFirst().setUseEnvironment(useEnvironment);
      try
      { Substitution substitution = (new ConsValue(operator, operands, operator.getSourceInfo())).accept(pair.getFirst());
        if (substitution != null)
        { subDefinitionEnvironment.addBinding(ellipsisString, new EllipsisBinding());
          Pair<RuntimeValue, EnvironmentValue> rewritten = pair.getSecond().getSecond().accept(new ASTMacroRewriteVisitor(substitution, subDefinitionEnvironment, useEnvironment, pair.getSecond().getFirst()));
          return rewritten.getFirst().accept(new ASTConvertVisitor(new CarbonCopyEnvironment(rewritten.getSecond(), useEnvironment), outputClass, mainClass));
        }
      } finally
      { pair.getFirst().setUseEnvironment(null);
      }
    }
    throw new SyntaxErrorException("Incorrect macro use for\n\n" + operands + "\n\n", operator.getSourceInfo());
  }
}
