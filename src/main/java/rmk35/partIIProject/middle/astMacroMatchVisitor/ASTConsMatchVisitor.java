package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;

import rmk35.partIIProject.middle.Environment;

import java.util.Map;
import java.util.Hashtable;

public class ASTConsMatchVisitor extends ASTNoMatchVisitor
{ ASTMatchVisitor carVisitor;
  ASTMatchVisitor cdrVisitor;

  public ASTConsMatchVisitor(ASTMatchVisitor carVisitor, ASTMatchVisitor cdrVisitor)
  { this.carVisitor = carVisitor;
    this.cdrVisitor = cdrVisitor;
  }

  @Override
  public Map<String, RuntimeValue> visit(ConsValue consCell)
  { Map<String, RuntimeValue> carSubstitution = consCell.getCar().accept(carVisitor);
    Map<String, RuntimeValue> cdrSubstitution = consCell.getCdr().accept(cdrVisitor);

    if (carSubstitution == null || cdrSubstitution == null) // NEXT 5 r7rs page 23: we want to have an ellipsis cons visitor that backtracks when cdrSubstitution == null
    { return null;
    } else
    { Map<String, RuntimeValue> returnValue = new Hashtable<>(carSubstitution);
      returnValue.putAll(cdrSubstitution);
      return returnValue;
    }
  }
}
