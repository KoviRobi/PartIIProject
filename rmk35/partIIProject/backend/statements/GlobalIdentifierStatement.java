package rmk35.partIIProject.backend.statements;

import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

public class GlobalIdentifierStatement extends IdentifierStatement
{ String name;
  String type;

  public GlobalIdentifierStatement(String name, String type)
  { this.name = name;
    this.type = type;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { output.addToPrimaryMethod("  getstatic " + name + " " + type + "\n");
    output.incrementStackCount(1);
  }
  public void generateSetOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { output.addToPrimaryMethod("  putstatic " + name + " " + type + "\n");
    output.decrementStackCount(1);
  }
}
