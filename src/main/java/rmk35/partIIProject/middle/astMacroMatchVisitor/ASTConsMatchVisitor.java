package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;

import rmk35.partIIProject.middle.Environment;

import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;

public class ASTConsMatchVisitor extends ASTNoMatchVisitor
{ ASTMatchVisitor carVisitor;
  ASTMatchVisitor cdrVisitor;

  public ASTConsMatchVisitor(ASTMatchVisitor carVisitor, ASTMatchVisitor cdrVisitor)
  { this.carVisitor = carVisitor;
    this.cdrVisitor = cdrVisitor;
  }

  @Override
  public Substitution visit(ConsValue consCell)
  { RuntimeValue car = consCell.getCar();
    RuntimeValue cdr = consCell.getCdr();
    Substitution carSubstitution = car.accept(carVisitor);
    Substitution cdrSubstitution = cdr.accept(cdrVisitor);

    if (carSubstitution == null || cdrSubstitution == null)
    { return null;
    } else
    { carSubstitution.merge(cdrSubstitution, consCell.getSourceInfo());
      return carSubstitution;
    }
  }
}
