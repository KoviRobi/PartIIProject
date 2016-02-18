package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.middle.Environment;

import java.util.Map;

public class ASTNoMatchVisitor extends ASTMatchVisitor
{ @Override
  public Map<String, RuntimeValue> visit(RuntimeValue value)
  { return null;
  }
}
