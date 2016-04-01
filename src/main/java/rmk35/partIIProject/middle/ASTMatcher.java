package rmk35.partIIProject.middle;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.utility.Pair;
import rmk35.partIIProject.utility.PerfectBinaryTree;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.frontend.SchemeParser;

import rmk35.partIIProject.middle.bindings.EllipsisBinding;
import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;
import rmk35.partIIProject.middle.astMacroMatchVisitor.ASTCompilePatternVisitor;
import rmk35.partIIProject.middle.astMacroMatchVisitor.ASTMatchVisitor;

import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Collection;

public class ASTMatcher
{ EnvironmentValue environment;
  ASTCompilePatternVisitor patternCompiler;

  Substitution match;
  Collection<String> nonLiterals;

  public ASTMatcher(String pattern, RuntimeValue body, String... literals)
  { environment = new EnvironmentValue(/* mutable */ true);
    environment.addBinding("...", new EllipsisBinding());
    patternCompiler = new ASTCompilePatternVisitor(new HashSet<>(Arrays.asList(literals)), environment);
    Pair<ASTMatchVisitor, Collection<String>> compiledPattern = parseData(pattern).accept(patternCompiler);
    nonLiterals = compiledPattern.getSecond();
    compiledPattern.getFirst().setUseEnvironment(environment);
    match = body.accept(compiledPattern.getFirst());
  }

  public boolean matched() { return match != null; }

  public PerfectBinaryTree<RuntimeValue> get(String key)
  { return (match.get(key) != null)
      ? match.get(key).clone()
      : null;
  }

  public RuntimeValue transform(String template)
  { // No rewriting if both environments are the same
    return parseData(template).accept(new ASTMacroRewriteVisitor(match, environment, environment, nonLiterals)).getFirst();
  }

  static RuntimeValue parseData(String pattern)
  { return SchemeParser.read(pattern);
  }

  @Override
  public String toString()
  { return match.toString();
  }
}
