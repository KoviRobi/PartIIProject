package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.SelfquotingValue;
// Literals
import rmk35.partIIProject.runtime.VectorValue;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.middle.bindings.EllipsisBinding;

import java.util.Collection;
import java.util.HashSet;

public class ASTCompilePatternVisitor extends ASTVisitor<Pair<ASTMatchVisitor, Collection<String>>>
{ Collection<String> literals;
  Collection<String> nonLiterals;
  Environment definitionEnvironment;

  public ASTCompilePatternVisitor(Collection<String> literals, Environment definitionEnvironment)
  { this.literals = literals;
    this.nonLiterals = new HashSet<>();
    this.definitionEnvironment = definitionEnvironment;
  }

  @Override
  public Pair<ASTMatchVisitor, Collection<String>> visit(ConsValue list)
  { RuntimeValue car = list.getCar();
    RuntimeValue cdr = list.getCdr();
    if (cdr instanceof ConsValue)
    { ConsValue cdrCons = (ConsValue) cdr;
      if (cdrCons.getCar() instanceof IdentifierValue)
      { IdentifierValue cadrIdentifier = (IdentifierValue) cdrCons.getCar();
        if (definitionEnvironment.lookUpSilent(cadrIdentifier.getValue()) instanceof EllipsisBinding)
        { return new Pair<>(new ASTConsStarMatchVisitor(car.accept(this).getFirst(), cdrCons.getCdr().accept(this).getFirst()), nonLiterals);
        }
      }
    }

    return new Pair<>(new ASTConsMatchVisitor(car.accept(this).getFirst(), cdr.accept(this).getFirst()), nonLiterals); // Possible solution: have ASTMatchVisitor return a sum type "ASTMatchVisitor + Substitution + NoMatch"
  }

  @Override
  public Pair<ASTMatchVisitor, Collection<String>> visit(IdentifierValue identifier)
  { if (literals.contains(identifier.getValue()))
    { return new Pair<>(new ASTLiteralIdentifierMatchVisitor(definitionEnvironment, identifier), nonLiterals);
    } else if (definitionEnvironment.lookUpSilent(identifier.getValue()) instanceof EllipsisBinding)
    { throw new SyntaxErrorException("Unexpected ellipsis", identifier.getSourceInfo());
    } else if (identifier.getValue().equals("_"))
    { return new Pair<>(new ASTAnyMatchVisitor(), nonLiterals);
    } else
    { nonLiterals.add(identifier.getValue());
      return new Pair<>(new ASTNonLiteralIdentifierMatchVisitor(identifier), nonLiterals);
    }
  }

  @Override
  public Pair<ASTMatchVisitor, Collection<String>> visit(NullValue nil)
  { return new Pair<>(new ASTNilMatchVisitor(), nonLiterals);
  }

  @Override
  public Pair<ASTMatchVisitor, Collection<String>> visit(SelfquotingValue object)
  { return new Pair<>(new ASTLiteralMatchVisitor(object), nonLiterals);
  }

  @Override
  public Pair<ASTMatchVisitor, Collection<String>> visit(VectorValue vector)
  { throw new UnsupportedOperationException("Vectors are not fully supported yet");
  }
}
