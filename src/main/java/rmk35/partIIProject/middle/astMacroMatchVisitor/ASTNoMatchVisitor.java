package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.SelfquotingValue;

import rmk35.partIIProject.middle.Environment;

import java.util.Map;

public class ASTNoMatchVisitor extends ASTMatchVisitor
{ @Override
  public Map<String, RuntimeValue> visit(ConsValue consCell)
  { return null;
  }

  @Override
  public Map<String, RuntimeValue> visit(IdentifierValue identifier)
  { return null;
  }

  @Override
  public Map<String, RuntimeValue> visit(NullValue nil)
  { return null;
  }

  @Override
  public Map<String, RuntimeValue> visit(SelfquotingValue object)
  { return null;
  }
}
