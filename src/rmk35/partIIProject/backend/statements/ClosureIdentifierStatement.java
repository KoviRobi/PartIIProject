package rmk35.partIIProject.backend.statements;

import java.util.Collection;
import java.util.TreeSet;
import java.util.Map;
import rmk35.partIIProject.backend.OutputClass;

import lombok.ToString;

/**
 * Closure variables are stored in the current object's field
 */
@ToString
public class ClosureIdentifierStatement extends IdentifierStatement
{ String name;
  String type;

  // FIXME: inline type if not needed later
  public ClosureIdentifierStatement(String name/*, String type*/)
  { this.name = name;
    this.type = "Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;";//type;
  }

  @Override
  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod("  ; ClosureIdentifierStatement Get\n");
    output.addToPrimaryMethod("  aload_0\n"); // 'this', the current object
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  getfield " + output.getName() + "/" + name + " " + type + "\n");
    output.incrementStackCount(2);
    output.addToPrimaryMethod("\n");
  }

  @Override
  public void generateSetOutput(OutputClass output)
  { output.addToPrimaryMethod("  ; ClosureIdentifierStatement Set\n");
    output.ensureFieldExists("private", name, type);
    output.addToPrimaryMethod("  aload_0\n"); // 'this', the current object
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  putfield " + output.getName() + "/" + name + " " + type + "\n");
    output.decrementStackCount(2); // Object, value popped
    output.addToPrimaryMethod("\n");
  }

  @Override
  public String getName()
  { return name;
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> returnValue = new TreeSet<>();
    returnValue.add(getName());
    return returnValue;
  }
}
