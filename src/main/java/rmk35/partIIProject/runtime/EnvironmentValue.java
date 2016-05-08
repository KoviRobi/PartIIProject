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
import rmk35.partIIProject.backend.InnerClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.SequenceStatement;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class EnvironmentValue implements RuntimeValue
{ Map<String, Binding> bindings = new HashMap<>();
  boolean mutable = false;
  List<Statement> initializer = new ArrayList<>();
  static Set<String> undefinedVariables = new HashSet<>();
  int level = 0;

  public EnvironmentValue() { }
  public EnvironmentValue(boolean mutable) { this.mutable = mutable; }
  public EnvironmentValue(EnvironmentValue other) { this(other, other.mutable()); }
  public EnvironmentValue(EnvironmentValue other, boolean mutable)
  { this.mutable = true;
    copyBindings(other);
    this.mutable = mutable;
  }

  public void copyBindings(EnvironmentValue other)
  { for (Map.Entry<String, Binding> binding : other.entrySet())
    { addBinding(binding.getKey(), binding.getValue());
    }
  }

  public EnvironmentValue subEnvironment()
  { EnvironmentValue returnValue = new EnvironmentValue(/* mutable */ true);
    for (Map.Entry<String, Binding> binding : entrySet())
    { returnValue.addBinding(binding.getKey(), binding.getValue().subEnvironment());
    }
    returnValue.level = level + 1;
    returnValue.setMutable(mutable);
    return returnValue;
  }

  public void addToInitializer(Statement statement)
  { initializer.add(statement);
  }

  public Statement getInitializer()
  { return new SequenceStatement(initializer);
  }

  public int getLevel() { return level; }

  public Set<Map.Entry<String, Binding>> entrySet() { return bindings.entrySet(); }
  public boolean contains(String identifier) { return bindings.containsKey(identifier); }
  public Binding getOrNull(String identifier) { return bindings.get(identifier); }

  public Binding getOrGlobal(MainClass mainClass, String identifier)
  { if (contains(identifier))
    { return bindings.get(identifier);
    } else
    { undefinedVariables.add(identifier);
      return addGlobalVariable(mainClass, identifier);
    }
  }

  public void warnUndefinedVariables()
  { for (String variable : undefinedVariables)
    { if (getOrNull(variable) == null)
      { System.out.println("Undefined variable \"" + variable + "\", I assumed it is a global variable.");
      }
    }
  }

  public <T extends Binding> T addBinding(String identifier, T binding)
  { if (! mutable) throw new UnsupportedOperationException("Trying to mutate an immutable EnvironmentValue");
    bindings.put(identifier, binding);
    return binding;
  }

  public GlobalBinding addGlobalVariable(MainClass mainClass, String identifier)
  { return addBinding(identifier, new GlobalBinding(mainClass.getName(), identifier, IdentifierValue.javaifyName(identifier)));
  }

  public LocalBinding addLocalVariable(OutputClass outputClass, String identifier)
  { return addBinding(identifier, new LocalBinding(outputClass.getName(), identifier, IdentifierValue.javaifyName(identifier)));
  }

  public Binding removeBinding(String identifier)
  { return bindings.remove(identifier);
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
