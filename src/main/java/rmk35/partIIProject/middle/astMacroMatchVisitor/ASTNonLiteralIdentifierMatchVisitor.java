package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.SelfquotingValue;

import java.util.Map;
import java.util.Hashtable;

public class ASTNonLiteralIdentifierMatchVisitor extends ASTMatchVisitor
{ String variableName;

  public ASTNonLiteralIdentifierMatchVisitor(String variableName)
  { this.variableName = variableName;
  }

  @Override
  public Map<String, RuntimeValue> visit(ConsValue consCell)
  { Map<String, RuntimeValue> returnValue = new Hashtable<>();
    returnValue.put(variableName, consCell);
    return returnValue;
  }

  @Override
  public Map<String, RuntimeValue> visit(IdentifierValue identifier)
  { Map<String, RuntimeValue> returnValue = new Hashtable<>();
    returnValue.put(variableName, identifier);
    return returnValue;
  }

  @Override
  public Map<String, RuntimeValue> visit(NullValue nil)
  { Map<String, RuntimeValue> returnValue = new Hashtable<>();
    returnValue.put(variableName, nil);
    return returnValue;
  }

  @Override
  public Map<String, RuntimeValue> visit(SelfquotingValue object)
  { Map<String, RuntimeValue> returnValue = new Hashtable<>();
    returnValue.put(variableName, object);
    return returnValue;
  }
}
