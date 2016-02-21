package rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.utility.Option;
import rmk35.partIIProject.utility.Some;
import rmk35.partIIProject.utility.None;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.IdentifierValue;

import java.util.Map;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import lombok.Data;

@Data
public class Substitution implements ASTMatchVisitorReturn
{ private Map<IdentifierValue, SubstitutionValue> substitutions;
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
    { substitutions.put(key, new SimpleSubstitutionValue(value));
    }
  }

  public Option<RuntimeValue> get(IdentifierValue key, Deque<Integer> path)
  { return substitutions.get(key).walk(new LinkedList<>(path)); // Clone path as walk mutates it
  }

  public boolean containsKey(IdentifierValue key)
  { return substitutions.containsKey(key);
  }

  public void merge(Substitution other)
  { for (Map.Entry<IdentifierValue, SubstitutionValue> element : other.substitutions.entrySet())
    { if (substitutions.containsKey(element.getKey()))
      { throw new SyntaxErrorException("Multiple occurrences of the symbol \"" + element.getKey().getValue() + "\" in a macro rule", element.getKey().getSourceInfo());
      }
      substitutions.put(element.getKey(), element.getValue());
    }
  }

  public void structuralMerge(Substitution other)
  { for (Map.Entry<IdentifierValue, SubstitutionValue> element : other.substitutions.entrySet())
    { if (!substitutions.containsKey(element.getKey()))
      { substitutions.put(element.getKey(), new StructuralSubstitutionValue());
      }
      substitutions.get(element.getKey()).merge(element.getKey(), element.getValue());
    }
  }

  // This is a variable length symmetric tree
  private interface SubstitutionValue
  { boolean isSameShape(SubstitutionValue other);
    void merge(IdentifierValue key, SubstitutionValue other);
    Option<RuntimeValue> walk(Deque<Integer> path);
  }
  //
  private class SimpleSubstitutionValue implements SubstitutionValue
  { private RuntimeValue value;
    public SimpleSubstitutionValue(RuntimeValue value) { this.value = value; }
    public boolean isSameShape(SubstitutionValue other) { return other instanceof SimpleSubstitutionValue; }
    public void merge(IdentifierValue key, SubstitutionValue other) // When structuralMerge is called for a non-structural element
    { throw new SyntaxErrorException("Multiple occurrences of the symbol \"" + key.getValue() + "\" in a macro rule", key.getSourceInfo());
    }
    public Option<RuntimeValue> walk(Deque<Integer> path) { return new Some<>(value); }
    @Override
    public String toString() { return value.toString(); }
  }
  //
  // Structural substitution value, to represent the structure of the match
  // e.g "a" in "((a ...) ...)" is at structure level 2 (level 0 is simple value)
  private class StructuralSubstitutionValue implements SubstitutionValue
  { private List<SubstitutionValue> children;
    public StructuralSubstitutionValue() { this.children = new ArrayList<>(); }
    public boolean isSameShape(SubstitutionValue other)
    { return other instanceof StructuralSubstitutionValue &&
        ((StructuralSubstitutionValue) other).children.size() == children.size();
    }
    public void merge(IdentifierValue key, SubstitutionValue other)
    { if (!children.isEmpty() && !children.get(0).isSameShape(other))
      { throw new InternalCompilerException("Somehow tried to merge \"" + other + "\" which is not of the same shape as \"" + children.get(0) + "\"");
      }
      children.add(other);
    }
    public Option<RuntimeValue> walk(Deque<Integer> path)
    { if (path.isEmpty()) { throw new InternalCompilerException("Empty path when walking the tree \"" + this + "\""); }
      if (path.getFirst() < children.size()) { return children.get(path.removeFirst()).walk(path); }
      return new None<RuntimeValue>();
    }
    @Override
    public String toString() { return children.toString(); }
  }
}
