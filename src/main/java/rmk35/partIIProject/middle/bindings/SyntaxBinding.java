package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.Environment;
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
{ Environment definitionEnvironment;
  Collection<String> literals;
  List<Pair<ASTMatchVisitor, RuntimeValue>> patternsAndTemplates;

  public SyntaxBinding(Environment definitionEnvironment, Collection<String> literals, List<Pair<RuntimeValue, RuntimeValue>> patternsAndTemplates)
  { this.definitionEnvironment = definitionEnvironment;
    this.literals = literals;
    this.patternsAndTemplates = new ArrayList<>(patternsAndTemplates.size());
    for (Pair<RuntimeValue, RuntimeValue> pair : patternsAndTemplates)
    { this.patternsAndTemplates.add(new Pair<>(pair.getFirst().accept(new ASTCompilePatternVisitor(literals, definitionEnvironment)), pair.getSecond()));
    }
  }

  @Override
  public Statement applicate(Environment useEnvironment, RuntimeValue operator, RuntimeValue operands)
  { // See Macros That Work, figure 3
    for (Pair<ASTMatchVisitor, RuntimeValue> pair : patternsAndTemplates)
    { pair.getFirst().setUseEnvironment(useEnvironment);
      Substitution substitution = (new ConsValue(operator, operands, operator.getSourceInfo())).accept(pair.getFirst());
      if (substitution != null)
      { Pair<RuntimeValue, Environment> rewritten = pair.getSecond().accept(new ASTMacroRewriteVisitor(substitution, definitionEnvironment, useEnvironment));
        return rewritten.getFirst().accept(new ASTConvertVisitor(rewritten.getSecond()));
      }
    }
    throw new SyntaxErrorException("Incorrect macro use", operator.getSourceInfo());
  }
}
