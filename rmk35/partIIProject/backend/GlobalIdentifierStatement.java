package rmk35.partIIProject.backend;

import java.util.Map;

public class GlobalIdentifierStatement extends IdentifierStatement
{ IdentifierValue value;
  String name;
  String type;

  public GlobalIdentifierStatement(IdentifierValue value, String name, String type)
  { this.value = value;
    this.name = name;
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
