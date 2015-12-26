package rmk35.partIIProject.backend.statements;

import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

public class LocalIdentifierStatement extends IdentifierStatement
{ String name;

  public LocalIdentifierStatement(String name)
  { this.name = name;
  }

  @Override
  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { output.addToPrimaryMethod("  ; LocalIdentifier Get\n");
    output.addToPrimaryMethod("  aload_1\n");
    output.incrementStackCount(1);
    output.addToPrimaryMethod("\n");
  }

  /* Assumes variable to set to is on top of the stack */
  @Override
  public void generateSetOutput(Map<IdentifierValue, Definition> definitions,
                                Map<IdentifierValue, Macro> macros,
                                OutputClass output)
  { output.addToPrimaryMethod("  ; LocalIdentifier Set\n");
    output.addToPrimaryMethod("  astore_1\n");
    output.decrementStackCount(1);
    output.addToPrimaryMethod("\n");
  }

  @Override
  public String getName()
  { return name;
  }
}
