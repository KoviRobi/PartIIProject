package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.SelfquotingValue;

import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;

public class ASTNonLiteralIdentifierMatchVisitor extends ASTMatchVisitor
{ IdentifierValue variable;

  public ASTNonLiteralIdentifierMatchVisitor(IdentifierValue variable)
  { this.variable = variable;
  }

  @Override
  public Substitution visit(ConsValue consCell)
  { Substitution returnValue = new Substitution();
    returnValue.put(variable.getValue(), variable.getSourceInfo(), consCell);
    return returnValue;
  }

  @Override
  public Substitution visit(IdentifierValue identifier)
  { Substitution returnValue = new Substitution();
    returnValue.put(variable.getValue(), variable.getSourceInfo(), identifier);
    return returnValue;
  }

  @Override
  public Substitution visit(NullValue nil)
  { Substitution returnValue = new Substitution();
    returnValue.put(variable.getValue(), variable.getSourceInfo(), nil);
    return returnValue;
  }

  @Override
  public Substitution visit(SelfquotingValue object)
  { Substitution returnValue = new Substitution();
    returnValue.put(variable.getValue(), variable.getSourceInfo(), object);
    return returnValue;
  }
}
