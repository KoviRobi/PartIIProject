package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;

/** A macro pattern gets compiled into this visitor, which is then matched against the macro application
    null means "no match"
 */
public abstract class ASTMatchVisitor extends ASTVisitor<Substitution>
{ EnvironmentValue useEnvironment = null;
  public void setUseEnvironment(EnvironmentValue environment)
  { useEnvironment = environment;
  }

  final EnvironmentValue getUseEnvironment()
  { if (useEnvironment == null) throw new InternalCompilerException("Macro use environment got before set");
    return useEnvironment;
  }
}
