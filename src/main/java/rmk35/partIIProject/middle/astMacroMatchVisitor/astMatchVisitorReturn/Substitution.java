package rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.utility.PerfectBinaryTree;
import rmk35.partIIProject.utility.PerfectBinaryTreeNode;
import rmk35.partIIProject.utility.PerfectBinaryTreeLeaf;

import rmk35.partIIProject.runtime.RuntimeValue;

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
{ Map<String, PerfectBinaryTree<RuntimeValue>> substitutions;

  public Substitution()
  { this.substitutions = new Hashtable<>();
  }

  public <T> T accept(ASTMatchVisitorReturnVisitor<T> visitor)
  { return visitor.visit(this);
  }

  public void put(String key, SourceInfo sourceInfo, RuntimeValue value)
  { if (substitutions.containsKey(key))
    { throw new SyntaxErrorException("Multiple occurrences of the symbol \"" + key + "\" in a macro rule", sourceInfo);
    } else
    { substitutions.put(key, new PerfectBinaryTreeLeaf<>(value));
    }
  }

  public PerfectBinaryTree<RuntimeValue> get(String key)
  { if (substitutions.get(key) != null)
    { return substitutions.get(key).clone();
    } else
    { return null;
    }
  }

  public boolean contains(String key)
  { return substitutions.containsKey(key);
  }

  public void merge(Substitution other, SourceInfo sourceInfo)
  { for (Map.Entry<String, PerfectBinaryTree<RuntimeValue>> element : other.substitutions.entrySet())
    { if (substitutions.containsKey(element.getKey()))
      { throw new SyntaxErrorException("Multiple occurrences of the symbol \"" + element.getKey() + "\" in a macro rule", sourceInfo);
      }
      substitutions.put(element.getKey(), element.getValue());
    }
  }

  public void structuralMerge(Substitution other)
  { for (Map.Entry<String, PerfectBinaryTree<RuntimeValue>> element : other.substitutions.entrySet())
    { if (!substitutions.containsKey(element.getKey()))
      { substitutions.put(element.getKey(), new PerfectBinaryTreeNode<>(other.substitutions.get(element.getKey())));
      } else
      { substitutions.get(element.getKey()).add(element.getValue());
      }
    }
  }
}
