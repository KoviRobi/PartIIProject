package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeLabelledData;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLiteral;
// Literals
import rmk35.partIIProject.frontend.AST.SchemeVector;
import rmk35.partIIProject.frontend.AST.SchemeAbbreviation;

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
  public ASTMatchVisitor visit(SchemeCons list)
  { // NEXT 5 r7rs page 23: have ellipsis visitor visit cdr, to see if next element is "..." and thus we would need to repeat current element
    ASTMatchVisitor car = list.car().accept(this);
    ASTMatchVisitor cdr = list.cdr().accept(this);
    return new ASTConsMatchVisitor(car, cdr); // Possible solution: have ASTMatchVisitor return a sum type "ASTMatchVisitor + Map<String, AST> + NoMatch"
  }

  @Override
  public ASTMatchVisitor visit(SchemeNil nil)
  { return new ASTNilMatchVisitor();
  }

  @Override
  public ASTMatchVisitor visit(SchemeIdentifier identifier)
  { if (definitionEnvironment.lookUpSilent(identifier.getData()) instanceof EllipsisBinding)
    { throw new SyntaxErrorException("Unexpected ellipsis", identifier.file(), identifier.line(), identifier.character());
    }
    else if (literals.contains(identifier.getData()))
    { return new ASTLiteralIdentifierMatchVisitor(definitionEnvironment, identifier);
    } else
    { return new ASTNonLiteralIdentifierMatchVisitor(identifier.getData());
    }
  }

  @Override
  public ASTMatchVisitor visit(SchemeLiteral object)
  { return new ASTLiteralMatchVisitor(object);
  }

  @Override
  public ASTMatchVisitor visit(SchemeVector vector)
  { throw new UnsupportedOperationException("Vectors are not fully supported yet");
  }

  @Override
  public ASTMatchVisitor visit(SchemeAbbreviation abbreviation) // This is a disguise for (quote ...)
  { throw new UnsupportedOperationException("Quotations are not fully supported yet");
  }

  @Override
  public ASTMatchVisitor visit(SchemeLabelReference reference)
  { throw new UnsupportedOperationException("Label references are not fully supported yet");
  }

  @Override
  public ASTMatchVisitor visit(SchemeLabelledData data)
  { throw new UnsupportedOperationException("Labelled data are not fully supported yet");
  }
}
