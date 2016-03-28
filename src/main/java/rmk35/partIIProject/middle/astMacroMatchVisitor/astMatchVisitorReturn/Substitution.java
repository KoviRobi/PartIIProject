package rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.utility.PerfectBinaryTree;
import rmk35.partIIProject.utility.PerfectBinaryTreeNode;
import rmk35.partIIProject.utility.PerfectBinaryTreeLeaf;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.IdentifierValue;

import java.util.Map;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Collection;

import lombok.Data;

@Data
public class Substitution implements ASTMatchVisitorReturn
{ private Map<IdentifierValue, PerfectBinaryTree<RuntimeValue>> substitutions;
  // For ellipses, each level (integer) is a set of structurally nested ellipses,
  // and each integer denotes the current number of matches

  public Substitution()
  { this.substitutions = new Hashtable<>();
  }

  public <T> T accept(ASTMatchVisitorReturnVisitor<T> visitor)
  { return visitor.visit(this);
  }

  public void put(IdentifierValue key, RuntimeValue value)
  { if (substitutions.containsKey(key))
    { throw new SyntaxErrorException("Multiple occurrences of the symbol \"" + key.getValue() + "\" in a macro rule", key.getSourceInfo());
    } else
    { substitutions.put(key, new PerfectBinaryTreeLeaf<>(value));
    }
  }

  public PerfectBinaryTree<RuntimeValue> get(IdentifierValue key)
  { return substitutions.get(key);
  }

  public boolean containsKey(IdentifierValue key)
  { return substitutions.containsKey(key);
  }

  public void merge(Substitution other)
  { for (Map.Entry<IdentifierValue, PerfectBinaryTree<RuntimeValue>> element : other.substitutions.entrySet())
    { if (substitutions.containsKey(element.getKey()))
      { throw new SyntaxErrorException("Multiple occurrences of the symbol \"" + element.getKey().getValue() + "\" in a macro rule", element.getKey().getSourceInfo());
      }
      substitutions.put(element.getKey(), element.getValue());
    }
  }

  public void structuralMerge(Substitution other)
  { for (Map.Entry<IdentifierValue, PerfectBinaryTree<RuntimeValue>> element : other.substitutions.entrySet())
    { if (!substitutions.containsKey(element.getKey()))
      { substitutions.put(element.getKey(), new PerfectBinaryTreeNode<>(other.substitutions.get(element.getKey())));
      } else
      { substitutions.put(element.getKey(), substitutions.get(element.getKey()).add(element.getValue()));
      }
    }
  }
}
