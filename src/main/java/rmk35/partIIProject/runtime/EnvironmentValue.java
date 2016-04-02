package rmk35.partIIProject.runtime;

import rmk35.partIIProject.SyntaxErrorException;
import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.bindings.LocalBinding;
import rmk35.partIIProject.middle.bindings.GlobalBinding;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.SequenceStatement;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class EnvironmentValue implements RuntimeValue
{ Map<String, Binding> bindings = new HashMap<>();
  boolean mutable = false;
  List<Statement> initializer = new ArrayList<>();
  int localsCount = 1; // Local 0 is 'this' for inner or String[] arguments for main methods

  public EnvironmentValue() { }
  public EnvironmentValue(boolean mutable) { this.mutable = mutable; }
  public EnvironmentValue(EnvironmentValue other) { this(other, other.mutable()); }
  public EnvironmentValue(EnvironmentValue other, boolean mutable)
  { this.mutable = true;
    for (Map.Entry<String, Binding> binding : other.entrySet())
    { addBinding(binding.getKey(), binding.getValue());
    }
    this.mutable = mutable;
  }

  public void addToInitializer(Statement statement)
  { initializer.add(statement);
  }

  public Statement getInitializer()
  { return new SequenceStatement(initializer);
  }

  public Set<Map.Entry<String, Binding>> entrySet() { return bindings.entrySet(); }
  public boolean contains(String identifier) { return bindings.containsKey(identifier); }
  public Binding getOrNull(String identifier) { return bindings.get(identifier); }

  public Binding getOrGlobal(String identifier)
  { if (contains(identifier))
    { return bindings.get(identifier);
    } else
    { System.err.println("Warning: unbound variable \"" + identifier + "\" found, assuming it is a global that has not been bound yet.");
      return addGlobalVariable(identifier);
    }
  }

  public <T extends Binding> T addBinding(String identifier, T binding)
  { if (! mutable) throw new UnsupportedOperationException("Trying to mutate an immutable EnvironmentValue");
    bindings.put(identifier, binding);
    return binding;
  }

  public LocalBinding addLocalVariable(String identifier)
  { LocalBinding returnValue = addBinding(identifier, new LocalBinding(identifier, localsCount));
    localsCount++;
    return returnValue;
  }

  public GlobalBinding addGlobalVariable(String identifier)
  { return addBinding(identifier, new GlobalBinding(identifier));
  }

  public Binding removeBinding(String identifier)
  { return bindings.remove(identifier);
  }

  public EnvironmentValue subEnvironment()
  { EnvironmentValue returnValue = new EnvironmentValue(/* mutable */ true);
    for (Map.Entry<String, Binding> binding : entrySet())
    { returnValue.addBinding(binding.getKey(), binding.getValue().subEnvironment());
    }
    returnValue.setMutable(mutable);
    return returnValue;
  }

  public String similarFreshKey(String key)
  { int i;
    for (i = 0; contains(key + i); i++) {}
    return key + i;
  }

  public void setMutable(boolean mutable)
  { this.mutable = mutable;
  }

  @Override
  public boolean mutable()
  { return mutable;
  }

  @Override
  public SourceInfo getSourceInfo() { return null; }

  @Override
  public boolean eq(RuntimeValue other) { return false; }
  @Override
  public boolean eqv(RuntimeValue other) { return false; }
  @Override
  public boolean equal(RuntimeValue other) { return false; }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  @Override
  public Object toJavaValue()
  { return this;
  }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { throw new InternalCompilerException("I don't know how to generate ByteCode for EnvironmentValue yet");
  }

  @Override
  public String toString()
  { return bindings.toString();
  }
}
