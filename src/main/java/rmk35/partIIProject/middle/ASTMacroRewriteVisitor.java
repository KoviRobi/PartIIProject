package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.utility.Pair;
import rmk35.partIIProject.utility.PerfectBinaryTree;
import rmk35.partIIProject.utility.PerfectBinaryTreeLeaf;
import rmk35.partIIProject.utility.PerfectBinaryTreeNode;

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

import java.util.Collection;
import java.util.function.Function;

public class ASTMacroRewriteVisitor extends ASTVisitor<Pair<RuntimeValue, Environment>>
{ Substitution substitution;
  Environment definitionEnvironment;
  Environment useEnvironment;
  Environment returnEnvironment;
  Collection<String> nonLiterals;

  public ASTMacroRewriteVisitor(Substitution substitution, Environment definitionEnvironment, Environment useEnvironment, Collection<String> nonLiterals)
  { this.substitution = substitution;
    this.definitionEnvironment = definitionEnvironment;
    this.useEnvironment = useEnvironment;
    this.returnEnvironment = new Environment(useEnvironment, /* subEnvironment */ false);
    this.nonLiterals = nonLiterals;
  }

  @Override
  public Pair<RuntimeValue, Environment> visit(RuntimeValue value)
  { Pair<PerfectBinaryTree<RuntimeValue>, Environment> internalValue = value.accept(new InternalASTMacroRewriteVisitor());
    // For multiple possibilities (as a result of matching ellipsis) we return a tree, but ellipsis in pattern remove a level of that tree
    // so we expect a leaf
    if (internalValue.getFirst().getDepth() != 0) throw new SyntaxErrorException("Not enough ellipses, got: " + internalValue.getFirst(), value.getSourceInfo());
    return new Pair<>(((PerfectBinaryTreeLeaf<RuntimeValue>) internalValue.getFirst()).getValue(), internalValue.getSecond());
  }

  private class InternalASTMacroRewriteVisitor extends ASTVisitor<Pair<PerfectBinaryTree<RuntimeValue>, Environment>>
  {
    @Override
    public Pair<PerfectBinaryTree<RuntimeValue>, Environment> visit(ConsValue list)
    { RuntimeValue car = list.getCar();
      RuntimeValue cdr = list.getCdr();
      // Escaping ellipses (... foo) is the same as foo without ellipsis binding, e.g. in (... ...) which should rewrite to ...
      if (car instanceof IdentifierValue)
      { IdentifierValue carIdentifier = (IdentifierValue) car;
        if (definitionEnvironment.lookUpSilent(carIdentifier.getValue()) instanceof EllipsisBinding)
        { Binding storedBinding = definitionEnvironment.removeBinding(carIdentifier.getValue());
          ConsValue cdrConsValue = (ConsValue) cdr;
          Pair<PerfectBinaryTree<RuntimeValue>, Environment> returnValue = cdrConsValue.getCar().accept(this);
          NullValue endNullValue = (NullValue) cdrConsValue.getCdr();
          definitionEnvironment.addBinding(carIdentifier.getValue(), storedBinding);
          return returnValue;
        }
      }
      // Trailing ellipses, e.g. in (x ...)
      if (cdr instanceof ConsValue)
      { ConsValue cdrCons = (ConsValue) cdr;
        if (cdrCons.getCar() instanceof IdentifierValue)
        { IdentifierValue cadrIdentifier = (IdentifierValue) cdrCons.getCar();
          if (definitionEnvironment.lookUpSilent(cadrIdentifier.getValue()) instanceof EllipsisBinding)
          { PerfectBinaryTree<RuntimeValue> carRewritten = car.accept(this).getFirst();
            PerfectBinaryTree<RuntimeValue> cddrRewritten = cdrCons.getCdr() // Ignore ellipsis
              .accept(this).getFirst();
            if (cddrRewritten == null) return null;
            if (carRewritten == null)
            { return new Pair<>(cddrRewritten, returnEnvironment);
            } else
            { PerfectBinaryTree<Function<RuntimeValue, RuntimeValue>> carFolded = carRewritten.foldRight(
                  (Function<RuntimeValue, RuntimeValue> acc, RuntimeValue element) -> x -> new ConsValue(element, acc.apply(x), list.getSourceInfo())
                , x -> x);
              return new Pair<>(PerfectBinaryTree.map(carFolded, cddrRewritten,
                (carFunction, cddrEnd) -> carFunction.apply(cddrEnd)), returnEnvironment);
            }
          }
        }
      }

      PerfectBinaryTree<RuntimeValue> carRewritten = car.accept(this).getFirst();
      PerfectBinaryTree<RuntimeValue> cdrRewritten = cdr.accept(this).getFirst();
      if (carRewritten == null || cdrRewritten == null) return null;
      return new Pair<>(PerfectBinaryTree.map(carRewritten, cdrRewritten,
        (carValue, cdrValue) -> new ConsValue(carValue, cdrValue, list.getSourceInfo())), returnEnvironment);
    }

    @Override
    public Pair<PerfectBinaryTree<RuntimeValue>, Environment> visit(IdentifierValue identifier)
    { String identifierName = identifier.getValue();
      Binding useBinding = useEnvironment.lookUpSilent(identifierName);
      Binding definitionBinding = definitionEnvironment.lookUpSilent(identifierName);
      // Don't rename if we don't have to
      String newIdentifierName =
        (useBinding == null || useBinding.equals(definitionBinding))
          ? identifierName
          : returnEnvironment.similarFreshKey(identifierName);
      // Note the use of definitionEnvironment, this works over macro definition
      // Also, lookUpSilent, as it will be looked up properly with ASTConvertVisitor, and don't want to modify definitionEnvironment
      if (definitionBinding != null)
      { returnEnvironment.addBinding(newIdentifierName, definitionBinding);
      }

      if (nonLiterals.contains(identifierName))
      { return new Pair<>(substitution.get(identifier), returnEnvironment); // May return null
      } else
      { return new Pair<>(new PerfectBinaryTreeLeaf<RuntimeValue>(new IdentifierValue(newIdentifierName, identifier.getSourceInfo())), returnEnvironment);
      }
    }

    @Override
    public Pair<PerfectBinaryTree<RuntimeValue>, Environment> visit(NullValue nil)
    { return new Pair<>(new PerfectBinaryTreeLeaf<RuntimeValue>(nil), returnEnvironment);
    }

    @Override
    public Pair<PerfectBinaryTree<RuntimeValue>, Environment> visit(SelfquotingValue object)
    { return new Pair<>(new PerfectBinaryTreeLeaf<RuntimeValue>(object), returnEnvironment);
    }

    @Override
    public Pair<PerfectBinaryTree<RuntimeValue>, Environment> visit(VectorValue vector)
    { throw new UnsupportedOperationException("Vectors are not fully supported yet");
    }
  }
}
