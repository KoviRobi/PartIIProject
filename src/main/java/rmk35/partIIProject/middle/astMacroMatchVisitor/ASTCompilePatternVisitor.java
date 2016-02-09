package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.SyntaxErrorException;

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
  { // NEXT 5 r7rs page 23: have ellipsis visitor visit cdr, to see if next element is "..." and thus we would need to repeat current element
    ASTMatchVisitor car = list.getCar().accept(this);
    ASTMatchVisitor cdr = list.getCdr().accept(this);
    return new ASTConsMatchVisitor(car, cdr); // Possible solution: have ASTMatchVisitor return a sum type "ASTMatchVisitor + Map<String, RuntimeValue> + NoMatch"
  }

  @Override
  public ASTMatchVisitor visit(IdentifierValue identifier)
  { if (definitionEnvironment.lookUpSilent(identifier.getValue()) instanceof EllipsisBinding)
    { throw new SyntaxErrorException("Unexpected ellipsis", identifier.getSourceInfo());
    }
    else if (literals.contains(identifier.getValue()))
    { return new ASTLiteralIdentifierMatchVisitor(definitionEnvironment, identifier);
    } else
    { return new ASTNonLiteralIdentifierMatchVisitor(identifier.getValue());
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
