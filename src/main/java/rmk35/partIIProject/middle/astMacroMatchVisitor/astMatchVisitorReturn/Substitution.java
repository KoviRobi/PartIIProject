package rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn;

import rmk35.partIIProject.middle.AST;

import java.util.Map;
import java.util.Hashtable;

public class Substitution implements ASTMatchVisitorReturn
{ Map<String, AST> substitutions;

  public Substitution()
  { this.substitutions = new Hashtable<>();
  }

  public <T> T accept(ASTMatchVisitorReturnVisitor<T> visitor)
  { return visitor.visit(this);
  }

  public void put(String key, AST value)
  { substitutions.put(key, value);
  }

  public AST get(String key)
  { return substitutions.get(key);
  }
}