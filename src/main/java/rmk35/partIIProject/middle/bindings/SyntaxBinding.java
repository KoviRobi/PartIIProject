package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.astMacroMatchVisitor.ASTCompilePatternVisitor;
import rmk35.partIIProject.middle.astMacroMatchVisitor.ASTMatchVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.ASTMacroRewriteVisitor;

import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;

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
  { this.definitionEnvironment = definitionEnvironment;
    this.literals = literals;
    this.patternsAndTemplates = new ArrayList<>(patternsAndTemplates.size());
    for (Pair<RuntimeValue, RuntimeValue> pair : patternsAndTemplates)
    { Pair<ASTMatchVisitor, Collection<String>> compiledPattern = pair.getFirst().accept(new ASTCompilePatternVisitor(literals, definitionEnvironment));
      this.patternsAndTemplates.add(new Pair<>(compiledPattern.getFirst(), new Pair<>(compiledPattern.getSecond(), pair.getSecond())));
    }
    this.ellipsisString = ellipsisString;
  }

  @Override
  public Statement applicate(EnvironmentValue useEnvironmentValue, RuntimeValue operator, RuntimeValue operands)
  { // See Macros That Work, figure 3
    for (Pair<ASTMatchVisitor, Pair<Collection<String>, RuntimeValue>> pair : patternsAndTemplates)
    { pair.getFirst().setUseEnvironment(useEnvironmentValue);
      Substitution substitution = (new ConsValue(operator, operands, operator.getSourceInfo())).accept(pair.getFirst());
      if (substitution != null)
      { Binding oldEllipsisBinding = definitionEnvironment.getOrNull(ellipsisString);
        definitionEnvironment.addBinding(ellipsisString, new EllipsisBinding());
        Pair<RuntimeValue, EnvironmentValue> rewritten = pair.getSecond().getSecond().accept(new ASTMacroRewriteVisitor(substitution, definitionEnvironment, useEnvironmentValue, pair.getSecond().getFirst()));
        if (oldEllipsisBinding == null)
        { definitionEnvironment.removeBinding(ellipsisString);
        } else
        { definitionEnvironment.addBinding(ellipsisString, oldEllipsisBinding);
        }
        return rewritten.getFirst().accept(new ASTConvertVisitor(rewritten.getSecond()));
      }
    }
    throw new SyntaxErrorException("Incorrect macro use", operator.getSourceInfo());
  }
}
