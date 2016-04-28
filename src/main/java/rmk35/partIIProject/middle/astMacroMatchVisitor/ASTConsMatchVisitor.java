package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;

public class ASTConsMatchVisitor extends ASTNoMatchVisitor
{ ASTMatchVisitor carVisitor;
  ASTMatchVisitor cdrVisitor;

  public ASTConsMatchVisitor(ASTMatchVisitor carVisitor, ASTMatchVisitor cdrVisitor)
  { this.carVisitor = carVisitor;
    this.cdrVisitor = cdrVisitor;
  }

  @Override
  public void setUseEnvironment(EnvironmentValue environment)
  { super.setUseEnvironment(environment);
    carVisitor.setUseEnvironment(environment);
    cdrVisitor.setUseEnvironment(environment);
  }

  @Override
  public Substitution visit(ConsValue consCell)
  { RuntimeValue car = consCell.getCar();
    Substitution carSubstitution = car.accept(carVisitor);

    if (carSubstitution == null)
    { return null;
    }

    RuntimeValue cdr = consCell.getCdr();
    Substitution cdrSubstitution = cdr.accept(cdrVisitor);

    if (cdrSubstitution == null)
    { return null;
    } else
    { carSubstitution.merge(cdrSubstitution, consCell.getSourceInfo());
      return carSubstitution;
    }
  }

  @Override
  public String toString()
  { return "(" + carVisitor.toString() + " . " + cdrVisitor.toString() + ")";
  }
}
