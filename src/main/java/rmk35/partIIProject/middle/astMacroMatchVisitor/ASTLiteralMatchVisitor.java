package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.SelfquotingValue;

import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;

public class ASTLiteralMatchVisitor extends ASTNoMatchVisitor
{ RuntimeValue storedObject;

  public ASTLiteralMatchVisitor(RuntimeValue object)
  { this.storedObject = object;
  }

  @Override
  public Substitution visit(SelfquotingValue object)
  { if (object.equal(storedObject))
    { return new Substitution();
    } else
    { return null;
    }
  }

  @Override
  public String toString()
  { return storedObject.toString();
  }
}
