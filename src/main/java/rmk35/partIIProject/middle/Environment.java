package rmk35.partIIProject.middle;

import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.bindings.GlobalBinding;
import rmk35.partIIProject.middle.bindings.LocalBinding;

import rmk35.partIIProject.backend.statements.Statement;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Environment
{ Map<String, Binding> bindings = new TreeMap<>();
  int localsCount = 1; // 'this' is local zero

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

  public Statement lookUpAsStatement(String identifier, String file, long line, long character)
  { return lookUp(identifier).toStatement(file, line, character);
  }

  public void addGlobalVariable(String identifier)
  { addBinding(identifier, new GlobalBinding(identifier));
  }

  public void addLocalVariable(String identifier)
  { addBinding(identifier, new LocalBinding(identifier, localsCount));
    localsCount++;
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

  public void addBinding(String identifier, Binding binding)
  { bindings.put(identifier, binding);
  }
}
