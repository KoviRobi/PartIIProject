package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.SelfquotingValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;

public class ASTConsStarMatchVisitor extends ASTMatchVisitor
{ ASTMatchVisitor carVisitor;
  ASTMatchVisitor cdrVisitor;

  public ASTConsStarMatchVisitor(ASTMatchVisitor carVisitor, ASTMatchVisitor cdrVisitor)
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
    RuntimeValue cdr = consCell.getCdr();
    Substitution carSubstitution = null;
    Substitution cdrSubstitution = null;
    Substitution returnSubstitution = new Substitution();

    // Try matching the rest with carVisitor
    do
    { Substitution tmpCarSubstitution = car.accept(carVisitor);
      Substitution tmpCdrSubstitution = cdr.accept(cdrVisitor);
      if (tmpCarSubstitution == null)
      { return null;
      } else
      { carSubstitution = tmpCarSubstitution;
      }
      if (tmpCdrSubstitution != null)
      { cdrSubstitution = tmpCdrSubstitution;
      }
      returnSubstitution.structuralMerge(carSubstitution);
      if (! (cdr instanceof ConsValue))
      { break; // E.g. if it is NullValue, because we have matched everything and cdrSubstitution is not null
      }
      car = ((ConsValue) cdr).getCar();
      cdr = ((ConsValue) cdr).getCdr();
    } while (true);

    if (cdrSubstitution == null)
    { return null;
    } else
    { returnSubstitution.merge(cdrSubstitution, consCell.getSourceInfo());
      return returnSubstitution;
    }
  }

  // When we get zero matches, e.g. matching (x ...) against () is valid
  @Override
  public Substitution visit(IdentifierValue identifier)
  { return identifier.accept(cdrVisitor);
  }

  @Override
  public Substitution visit(NullValue nullValue)
  { return nullValue.accept(cdrVisitor);
  }

  @Override
  protected Substitution visit(SelfquotingValue object)
  { return object.accept(cdrVisitor);
  }

  @Override
  public String toString()
  { return "(" + carVisitor.toString() + " ... . " + cdrVisitor.toString() + ")";
  }
}
