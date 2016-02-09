package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.NullValue;

import java.util.Map;
import java.util.Hashtable;

public class ASTNilMatchVisitor extends ASTNoMatchVisitor
{ @Override
  public Map<String, RuntimeValue> visit(NullValue nil)
  { return new Hashtable<>();
  }
}
