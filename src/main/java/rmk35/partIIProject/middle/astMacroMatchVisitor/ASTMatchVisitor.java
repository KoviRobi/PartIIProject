package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;

import java.util.Map;

/** A macro pattern gets compiled into this visitor, which is then matched against the macro application
    null means "no match"
 */
public abstract class ASTMatchVisitor extends ASTVisitor<Map<String, AST>>
{ Environment useEnvironment = null;
  public final void setUseEnvironment(Environment environment)
  { useEnvironment = environment;
  }

  final Environment getUseEnvironment()
  { if (useEnvironment == null) throw new InternalCompilerException("Macro use environment got before set");
    return useEnvironment;
  }
}
