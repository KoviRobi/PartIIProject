package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;

import rmk35.partIIProject.middle.Environment;

import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;

public class ASTConsStarMatchVisitor extends ASTNoMatchVisitor
{ ASTMatchVisitor carVisitor;
  ASTMatchVisitor cdrVisitor;

  public ASTConsStarMatchVisitor(ASTMatchVisitor carVisitor, ASTMatchVisitor cdrVisitor)
  { this.carVisitor = carVisitor;
    this.cdrVisitor = cdrVisitor;
  }

  @Override
  public Substitution visit(ConsValue consCell)
  { try
    { RuntimeValue car = consCell.getCar();
      RuntimeValue cdr = consCell.getCdr();
      Substitution carSubstitution = car.accept(carVisitor);
      Substitution cdrSubstitution = cdr.accept(cdrVisitor);
  
      if (carSubstitution == null)
      { return null;
      }
      Substitution carStarSubstitution = new Substitution();
      carStarSubstitution.structuralMerge(carSubstitution);
  
      // Try matching the rest with carVisitor
      while (cdrSubstitution == null && cdr instanceof ConsValue)
      { car = ((ConsValue) cdr).getCar();
        cdr = ((ConsValue) cdr).getCdr();
  
        Substitution newCarSubstitution = car.accept(carVisitor);
        if (newCarSubstitution == null)
        { return null;
        }
        carStarSubstitution.structuralMerge(newCarSubstitution);
        cdrSubstitution = cdr.accept(cdrVisitor);
      }
  
      if (cdrSubstitution == null)
      { return null;
      } else
      { carStarSubstitution.merge(cdrSubstitution);
        return carStarSubstitution;
      }
    } finally
    {
    }
  }
}
