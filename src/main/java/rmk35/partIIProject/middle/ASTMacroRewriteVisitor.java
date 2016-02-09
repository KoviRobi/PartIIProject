package rmk35.partIIProject.middle;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.SelfquotingValue;
// SelfquotingValues
import rmk35.partIIProject.runtime.BooleanValue;
import rmk35.partIIProject.runtime.BytevectorValue;
import rmk35.partIIProject.runtime.CharacterValue;
import rmk35.partIIProject.runtime.NumberValue;
import rmk35.partIIProject.runtime.StringValue;
import rmk35.partIIProject.runtime.VectorValue;

import rmk35.partIIProject.middle.bindings.Binding;

import java.util.Map;

public class ASTMacroRewriteVisitor extends ASTVisitor<Pair<RuntimeValue, Environment>>
{ Map<String, RuntimeValue> substitution;
  Environment definitionEnvironment;
  Environment useEnvironment;
  Environment returnEnvironment;

  public ASTMacroRewriteVisitor(Map<String, RuntimeValue> substitution, Environment definitionEnvironment, Environment useEnvironment)
  { this.substitution = substitution;
    this.definitionEnvironment = definitionEnvironment;
    this.useEnvironment = useEnvironment;
    this.returnEnvironment = new Environment(useEnvironment, /* subEnvironment */ false);
  }

  @Override
  public Pair<RuntimeValue, Environment> visit(ConsValue list)
  { Pair<RuntimeValue, Environment> car = list.getCar().accept(this);
    Pair<RuntimeValue, Environment> cdr = list.getCdr().accept(this);
    return new Pair<>(new ConsValue(car.getFirst(), cdr.getFirst(), list.getSourceInfo()), returnEnvironment);
  }

  @Override
  public Pair<RuntimeValue, Environment> visit(IdentifierValue identifier)
  { String identifierName = identifier.getValue();
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
    { return new Pair<>(new IdentifierValue(newIdentifierName, identifier.getSourceInfo()), returnEnvironment);
    }
  }

  @Override
  public Pair<RuntimeValue, Environment> visit(NullValue nil)
  { return new Pair<>(nil, returnEnvironment);
  }

  @Override
  public Pair<RuntimeValue, Environment> visit(SelfquotingValue object)
  { return new Pair<>(object, returnEnvironment);
  }

  @Override
  public Pair<RuntimeValue, Environment> visit(VectorValue vector)
  { throw new UnsupportedOperationException("Vectors are not fully supported yet");
  }
}
