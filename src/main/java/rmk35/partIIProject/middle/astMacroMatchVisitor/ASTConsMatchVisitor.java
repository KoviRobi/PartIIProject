package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;

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
  public Map<String, AST> visit(SchemeCons consCell)
  { Map<String, AST> carSubstitution = consCell.car().accept(carVisitor);
    Map<String, AST> cdrSubstitution = consCell.cdr().accept(cdrVisitor);

    if (carSubstitution == null || cdrSubstitution == null) // NEXT 5 r7rs page 23: we want to have an ellipsis cons visitor that backtracks when cdrSubstitution == null
    { return null;
    } else
    { Map<String, AST> returnValue = new Hashtable<>(carSubstitution);
      returnValue.putAll(cdrSubstitution);
      return returnValue;
    }
  }
}
