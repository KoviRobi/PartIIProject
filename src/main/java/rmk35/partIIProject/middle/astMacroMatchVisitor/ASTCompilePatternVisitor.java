package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.SelfquotingValue;
// Literals
import rmk35.partIIProject.runtime.VectorValue;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.middle.bindings.EllipsisBinding;

import java.util.Collection;

public class ASTCompilePatternVisitor extends ASTVisitor<ASTMatchVisitor>
{ Collection<String> literals;
  Environment definitionEnvironment;

  public ASTCompilePatternVisitor(Collection<String> literals, Environment definitionEnvironment)
  { this.literals = literals;
    this.definitionEnvironment = definitionEnvironment;
  }

  @Override
  public ASTMatchVisitor visit(ConsValue list)
  { RuntimeValue car = list.getCar();
    RuntimeValue cdr = list.getCdr();
    if (cdr instanceof ConsValue)
    { ConsValue cdrCons = (ConsValue) cdr;
      if (cdrCons.getCar() instanceof IdentifierValue)
      { IdentifierValue cadrIdentifier = (IdentifierValue) cdrCons.getCar();
        if (definitionEnvironment.lookUpSilent(cadrIdentifier.getValue()) instanceof EllipsisBinding)
        { return new ASTConsStarMatchVisitor(car.accept(this), cdrCons.getCdr().accept(this));
        }
      }
    }

    return new ASTConsMatchVisitor(car.accept(this), cdr.accept(this)); // Possible solution: have ASTMatchVisitor return a sum type "ASTMatchVisitor + Substitution + NoMatch"
  }

  @Override
  public ASTMatchVisitor visit(IdentifierValue identifier)
  { if (literals.contains(identifier.getValue()))
    { return new ASTLiteralIdentifierMatchVisitor(definitionEnvironment, identifier);
    } else if (definitionEnvironment.lookUpSilent(identifier.getValue()) instanceof EllipsisBinding)
    { throw new SyntaxErrorException("Unexpected ellipsis", identifier.getSourceInfo());
    } else if (identifier.getValue().equals("_"))
    { return new ASTAnyMatchVisitor();
    } else
    { return new ASTNonLiteralIdentifierMatchVisitor(identifier);
    }
  }

  @Override
  public ASTMatchVisitor visit(NullValue nil)
  { return new ASTNilMatchVisitor();
  }

  @Override
  public ASTMatchVisitor visit(SelfquotingValue object)
  { return new ASTLiteralMatchVisitor(object);
  }

  @Override
  public ASTMatchVisitor visit(VectorValue vector)
  { throw new UnsupportedOperationException("Vectors are not fully supported yet");
  }
}
