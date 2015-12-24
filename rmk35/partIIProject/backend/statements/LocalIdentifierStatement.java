package rmk35.partIIProject.backend.statements;

import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

public class LocalIdentifierStatement extends IdentifierStatement
{ IdentifierValue value;

  public LocalIdentifierStatement(IdentifierValue value)
  { this.value = value;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { output.addToPrimaryMethod("  aload_1\n");
    output.incrementStackCount(1);
  }

  /* Assumes variable to set to is on top of the stack */
  public void generateSetOutput(Map<IdentifierValue, Definition> definitions,
                                Map<IdentifierValue, Macro> macros,
                                OutputClass output)
  { output.addToPrimaryMethod("  astore_1\n");
    output.decrementStackCount(1);
  }
}
