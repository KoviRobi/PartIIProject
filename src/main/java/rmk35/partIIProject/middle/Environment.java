package rmk35.partIIProject.middle;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.bindings.GlobalBinding;
import rmk35.partIIProject.middle.bindings.LocalBinding;

import rmk35.partIIProject.backend.statements.Statement;

import java.util.Collection;
import java.util.Map;
import java.util.Hashtable;

public class Environment
{ Map<String, Binding> bindings = new Hashtable<>();
  int localsCount = 1; // 'this' is local zero (or String[] arguments for MainClass)

  public Environment(){}
  // SubEnvironment is true when we enter a lambda (converts local bindings to closure bindings)
  public Environment(Environment outerEnvironment, boolean subEnvironment)
  { for (Map.Entry<String, Binding> entry : outerEnvironment.bindings.entrySet())
    { bindings.put(entry.getKey(),
        subEnvironment
        ? entry.getValue().subEnvironment()
        : entry.getValue());
    }
  }

  public boolean contains(String identifier)
  { return bindings.containsKey(identifier);
  }

  public Binding lookUp(String identifier)
  { Binding binding = bindings.get(identifier);
    if (binding == null)
    { System.err.println("Unbound variable " + identifier + " found, assuming it is a global that has not been bound yet.");
      Binding returnValue = new GlobalBinding(identifier);
      bindings.put(identifier, returnValue); // Cache to avoid further warnings for this identifier
      return returnValue;
    } else
    { return binding;
    }
  }

  public Binding lookUpSilent(String identifier)
  { return bindings.get(identifier);
  }

  public Statement lookUpAsStatement(String identifier, SourceInfo sourceInfo)
  { return lookUp(identifier).toStatement(sourceInfo);
  }

  public GlobalBinding addGlobalVariable(String identifier)
  { GlobalBinding binding = new GlobalBinding(identifier);
    addBinding(identifier, binding);
    return binding;
  }

  public LocalBinding addLocalVariable(String identifier)
  { LocalBinding binding = new LocalBinding(identifier, localsCount);
    addBinding(identifier, binding);
    localsCount++;
    return binding;
  }

  public void addGlobalVariables(Collection<String> identifiers)
  { for (String identifier : identifiers)
    { addGlobalVariable(identifier);
    }
  }

  public void addLocalVariables(Collection<String> identifiers)
  { for (String identifier : identifiers)
    { addLocalVariable(identifier);
    }
  }

  public Binding addBinding(String identifier, Binding binding)
  { bindings.put(identifier, binding);
    return binding;
  }

  public Binding removeBinding(String identifier)
  { return bindings.remove(identifier);
  }

  // For macro rewriting, when we need fresh keys
  public String similarFreshKey(String key)
  { int i = 0;
    for (i = 0; bindings.containsKey(key + i); i++) {}
    return key + i;
  }

  @Override
  public String toString()
  { return "Environment: \"" + bindings + "\"";
  }
}
