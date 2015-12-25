package rmk35.partIIProject.backend.statements;

import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

public class GlobalIdentifierStatement extends IdentifierStatement
{ String name;
  String type;

  // FIXME: inline type if not needed later
  public GlobalIdentifierStatement(String name/*, String type*/)
  { this.name = name;
    this.type = "Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;";//type;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { output.addToPrimaryMethod("  ; GlobalIdentifierStatement Get\n");
    output.addToPrimaryMethod("  getstatic " + output.getMainClass().getName() + "/" + name + " " + type + "\n");
    output.incrementStackCount(1);
    output.addToPrimaryMethod("\n");
  }
  public void generateSetOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { output.addToPrimaryMethod("  ; GlobalIdentifierStatement Set\n");
    output.ensureFieldExists("private static", name, type);
    output.addToPrimaryMethod("  putstatic " + output.getMainClass().getName() + "/" + name + " " + type + "\n");
    output.decrementStackCount(1);
    output.addToPrimaryMethod("\n");
  }
}
