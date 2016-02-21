package rmk35.partIIProject.middle;

import rmk35.partIIProject.utility.Pair;
import rmk35.partIIProject.utility.Option;
import rmk35.partIIProject.utility.Some;
import rmk35.partIIProject.utility.None;

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
import rmk35.partIIProject.middle.bindings.EllipsisBinding;

import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;

import java.util.Deque;
import java.util.LinkedList;

public class ASTMacroRewriteVisitor extends ASTVisitor<Pair<RuntimeValue, Environment>>
{ Substitution substitution;
  Environment definitionEnvironment;
  Environment useEnvironment;
  Environment returnEnvironment;
  Deque<Integer> ellipsisStructure;

  public ASTMacroRewriteVisitor(Substitution substitution, Environment definitionEnvironment, Environment useEnvironment)
  { this.substitution = substitution;
    this.definitionEnvironment = definitionEnvironment;
    this.useEnvironment = useEnvironment;
    this.returnEnvironment = new Environment(useEnvironment, /* subEnvironment */ false);
    this.ellipsisStructure = new LinkedList<>();
  }

  @Override
  public Pair<RuntimeValue, Environment> visit(RuntimeValue value)
  { Pair<Option<RuntimeValue>, Environment> internalValue = value.accept(new InternalASTMacroRewriteVisitor());
    // Option None used to represent no more substitutions for an ellipsis substitution
    // E.g. after matching (x ...) against (1 2), when rewriting (x ...) we get Some(1), Some(2) and None
    return new Pair<>(internalValue.getFirst().getValue(), internalValue.getSecond());
  }

  private class InternalASTMacroRewriteVisitor extends ASTVisitor<Pair<Option<RuntimeValue>, Environment>>
  {
    @Override
    public Pair<Option<RuntimeValue>, Environment> visit(ConsValue list)
    { RuntimeValue car = list.getCar();
      RuntimeValue cdr = list.getCdr();
      if (cdr instanceof ConsValue)
      { ConsValue cdrCons = (ConsValue) cdr;
        if (cdrCons.getCar() instanceof IdentifierValue)
        { IdentifierValue cadrIdentifier = (IdentifierValue) cdrCons.getCar();
          if (definitionEnvironment.lookUpSilent(cadrIdentifier.getValue()) instanceof EllipsisBinding)
          { ellipsisStructure.addLast(0);
            ConsValue head = null;
            ConsValue tail = null;
            Option<RuntimeValue> carRewritten = car.accept(this).getFirst();

            while (carRewritten.isSome())
            { ConsValue newTail = new ConsValue(carRewritten.getValue(), null, list.getSourceInfo());
              if (head == null) { head = newTail; }
              if (tail != null) { tail.setCdr(newTail); }
              tail = newTail;
              ellipsisStructure.addLast(ellipsisStructure.removeLast() + 1);
              carRewritten = car.accept(this).getFirst();
            }
            ellipsisStructure.removeLast();

            RuntimeValue rest = cdrCons.getCdr(); // Ignore ellipsis
            Option<RuntimeValue> restRewritten = rest.accept(this).getFirst();
            if (head == null /* carRewritten always None */ || !restRewritten.isSome())
            { return new Pair<>(new None<>(), returnEnvironment);
            }
            tail.setCdr(restRewritten.getValue());
            return new Pair<>(new Some<>(head), returnEnvironment);
          }
        }
      }

      Option<RuntimeValue> carRewritten = car.accept(this).getFirst();
      Option<RuntimeValue> cdrRewritten = cdr.accept(this).getFirst();
      if (!carRewritten.isSome() || !cdrRewritten.isSome())
      { return new Pair<>(new None<>(), returnEnvironment);
      }
      return new Pair<>(new Some<>(new ConsValue(carRewritten.getValue(), cdrRewritten.getValue(), list.getSourceInfo())), returnEnvironment);
    }

    @Override
    public Pair<Option<RuntimeValue>, Environment> visit(IdentifierValue identifier)
    { String identifierName = identifier.getValue();
      String newIdentifierName = returnEnvironment.similarFreshKey(identifierName);
      // Note the use of definitionEnvironment, this works over macro definition
      // Also, lookUpSilent, as it will be looked up properly with ASTConvertVisitor, and don't want to modify definitionEnvironment
      Binding binding = definitionEnvironment.lookUpSilent(identifierName);
      if (binding != null)
      { returnEnvironment.addBinding(newIdentifierName, binding);
      }

      if (substitution.containsKey(identifier))
      { return new Pair<>(substitution.get(identifier, ellipsisStructure), returnEnvironment);
      } else
      { return new Pair<>(new Some<>(new IdentifierValue(newIdentifierName, identifier.getSourceInfo())), returnEnvironment);
      }
    }

    @Override
    public Pair<Option<RuntimeValue>, Environment> visit(NullValue nil)
    { return new Pair<>(new Some<>(nil), returnEnvironment);
    }

    @Override
    public Pair<Option<RuntimeValue>, Environment> visit(SelfquotingValue object)
    { return new Pair<>(new Some<>(object), returnEnvironment);
    }

    @Override
    public Pair<Option<RuntimeValue>, Environment> visit(VectorValue vector)
    { throw new UnsupportedOperationException("Vectors are not fully supported yet");
    }
  }
}
