package rmk35.partIIProject.runtime.libraries;

import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.bindings.LocalBinding;
import rmk35.partIIProject.middle.bindings.GlobalBinding;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.statements.Statement;

import java.util.Set;
import java.util.Map;

public class CarbonCopyEnvironment extends EnvironmentValue
{ EnvironmentValue original;
  EnvironmentValue copy;

  public CarbonCopyEnvironment(EnvironmentValue original, EnvironmentValue copy)
  { this.copy = copy;
    this.original = original;
  }

  public void addToInitializer(Statement statement)
  { copy.addToInitializer(statement);
    original.addToInitializer(statement);
  }

  public Statement getInitializer()
  { return original.getInitializer();
  }

  public Set<Map.Entry<String, Binding>> entrySet() { return original.entrySet(); }
  public boolean contains(String identifier) { return original.contains(identifier); }
  public Binding getOrNull(String identifier) { return original.getOrNull(identifier); }
  public Binding getOrGlobal(MainClass mainClass, String identifier) { return original.getOrGlobal(mainClass, identifier); }

  public <T extends Binding> T addBinding(String identifier, T binding)
  { copy.addBinding(identifier, binding);
    return original.addBinding(identifier, binding);
  }

  public GlobalBinding addGlobalVariable(MainClass mainClass, String identifier)
  { copy.addGlobalVariable(mainClass, identifier);
    return original.addGlobalVariable(mainClass, identifier);
  }

  public LocalBinding addLocalVariable(OutputClass outputClass, String identifier)
  { copy.addLocalVariable(outputClass, identifier);
    return original.addLocalVariable(outputClass, identifier);
  }

  public Binding removeBinding(String identifier)
  { copy.removeBinding(identifier);
    return original.removeBinding(identifier);
  }

  public EnvironmentValue subEnvironment()
  { return original.subEnvironment();
  }

  public String similarFreshKey(String key)
  { int i;
    for (i = 0; copy.contains(key + i) || original.contains(key + i); i++) {}
    return key + i;
  }

  public void setMutable(boolean mutable)
  { copy.setMutable(mutable);
    original.setMutable(mutable);
  }

  @Override
  public boolean mutable()
  { return copy.mutable() && original.mutable();
  }

  @Override
  public SourceInfo getSourceInfo() { return null; }
}