package rmk35.partIIProject.middle;

import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.bindings.GlobalBinding;
import rmk35.partIIProject.middle.bindings.LocalBinding;

import rmk35.partIIProject.backend.statements.Statement;

import java.util.Map;
import java.util.TreeMap;

/* This is an immutable environment, with adding variables returning a new environment.
    It's immutability is necessary for the operation of the ASTVisitor
    */

public class Environment
{ Map<String, Binding> bindings;
  int localsCount = 1; // 'this' is local zero

  public Environment()
  { bindings = new TreeMap<>();
  }

  public Environment(Environment outerEnvironment)
  { // FIXME: make local variables closure
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

  public Statement lookUpAsStatement(String identifier)
  { return lookUp(identifier).toStatement();
  }

  public void addGlobalVariables(String identifier)
  { new GlobalBinding(identifier);
  }

  public void addLocalVariables(String identifier)
  { new LocalBinding(identifier, localsCount);
    localsCount++;
  }

  public void addBinding(String identifier, Binding binding)
  { bindings.put(identifier, binding);
  }
}
