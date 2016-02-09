package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.SelfquotingValue;

import java.util.Map;
import java.util.Hashtable;

public class ASTLiteralMatchVisitor extends ASTNoMatchVisitor
{ RuntimeValue storedObject;

  public ASTLiteralMatchVisitor(RuntimeValue object)
  { this.storedObject = object;
  }

  @Override
  public Map<String, RuntimeValue> visit(SelfquotingValue object)
  { object.equal(storedObject);
    return new Hashtable<>();
  }
}
