package rmk35.partIIProject.backend.statements;

import java.util.Collection;
import java.util.TreeSet;
import java.util.Map;
import rmk35.partIIProject.backend.OutputClass;

import lombok.ToString;

@ToString
public class GlobalIdentifierStatement extends IdentifierStatement
{ String name;
  String type;

  // FIXME: inline type if not needed later
  public GlobalIdentifierStatement(String name/*, String type*/)
  { this.name = name;
    this.type = "Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;";//type;
  }

  @Override
  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod("  ; GlobalIdentifierStatement Get\n");
    output.addToPrimaryMethod("  getstatic " + output.getMainClass().getName() + "/" + name + " " + type + "\n");
    output.incrementStackCount(1);
    output.addToPrimaryMethod("\n");
  }

  @Override
  public void generateSetOutput(OutputClass output)
  { output.addToPrimaryMethod("  ; GlobalIdentifierStatement Set\n");
    output.ensureFieldExists("private static", name, type);
    output.addToPrimaryMethod("  putstatic " + output.getMainClass().getName() + "/" + name + " " + type + "\n");
    output.decrementStackCount(1);
    output.addToPrimaryMethod("\n");
  }

  @Override
  public String getName()
  { return name;
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
