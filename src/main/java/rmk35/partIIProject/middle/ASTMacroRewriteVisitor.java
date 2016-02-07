package rmk35.partIIProject.middle;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.frontend.AST.SchemeLabelledData;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLiteral;
// Literals
import rmk35.partIIProject.frontend.AST.SchemeAbbreviation;
import rmk35.partIIProject.frontend.AST.SchemeBoolean;
import rmk35.partIIProject.frontend.AST.SchemeBytevector;
import rmk35.partIIProject.frontend.AST.SchemeCharacter;
import rmk35.partIIProject.frontend.AST.SchemeNumber;
import rmk35.partIIProject.frontend.AST.SchemeString;
import rmk35.partIIProject.frontend.AST.SchemeVector;

import rmk35.partIIProject.middle.bindings.Binding;

import java.util.Map;

public class ASTMacroRewriteVisitor extends ASTVisitor<Pair<AST, Environment>>
{ Map<String, AST> substitution;
  Environment definitionEnvironment;
  Environment useEnvironment;
  Environment returnEnvironment;

  public ASTMacroRewriteVisitor(Map<String, AST> substitution, Environment definitionEnvironment, Environment useEnvironment)
  { this.substitution = substitution;
    this.definitionEnvironment = definitionEnvironment;
    this.useEnvironment = useEnvironment;
    this.returnEnvironment = new Environment(useEnvironment, /* subEnvironment */ false);
  }

  @Override
  public Pair<AST, Environment> visit(SchemeCons list)
  { Pair<AST, Environment> car = list.car().accept(this);
    Pair<AST, Environment> cdr = list.cdr().accept(this);
    return new Pair<>(new SchemeCons(car.getFirst(), cdr.getFirst(), list.file(), list.line(), list.character()), returnEnvironment);
  }

  @Override
  public Pair<AST, Environment> visit(SchemeNil nil)
  { return new Pair<>(nil, returnEnvironment);
  }

  @Override
  public Pair<AST, Environment> visit(SchemeIdentifier identifier)
  { String identifierName = identifier.getData();
    String newIdentifierName = returnEnvironment.similarFreshKey(identifierName);
    // Note the use of definitionEnvironment, this works over macro definition
    // Also, lookUpSilent, as it will be looked up properly with ASTConvertVisitor, and don't want to modify definitionEnvironment
    Binding binding = definitionEnvironment.lookUpSilent(identifierName);
    if (binding != null)
    { returnEnvironment.addBinding(newIdentifierName, binding);
    }

    if (substitution.containsKey(identifierName))
    { return new Pair<>(substitution.get(identifierName), returnEnvironment);
    } else
    { return new Pair<>(new SchemeIdentifier(newIdentifierName, identifier.file(), identifier.line(), identifier.character()), returnEnvironment);
    }
  }

  @Override
  public Pair<AST, Environment> visit(SchemeLabelReference reference)
  { throw new UnsupportedOperationException("Labels are not fully supported yet");
  }

  @Override
  public Pair<AST, Environment> visit(SchemeLabelledData data)
  { throw new UnsupportedOperationException("Labelled datum are not fully supported yet");
  }

  @Override
  public Pair<AST, Environment> visit(SchemeLiteral object)
  { return new Pair<>(object, returnEnvironment);
  }

  @Override
  public Pair<AST, Environment> visit(SchemeVector vector)
  { throw new UnsupportedOperationException("Vectors are not fully supported yet");
  }

  @Override
  public Pair<AST, Environment> visit(SchemeAbbreviation abbreviation) // This is a disguise for (quote ...)
  { throw new UnsupportedOperationException("Quotations are not fully supported yet");
  }
}
