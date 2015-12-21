package rmk35.partIIProject.backend;

import java.util.Map;

public class LocalIdentifierStatement extends IdentifierStatement
{ IdentifierValue value;

  public LocalIdentifierStatement(IdentifierValue value)
  { this.value = value;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { output.addToPrimaryMethod("  aload_1");
    output.incrementStackCount(1);
  }

  /* Assumes variable to set to is on top of the stack */
  public void generateSetOutput(Map<IdentifierValue, Definition> definitions,
                                Map<IdentifierValue, Macro> macros,
                                OutputClass output)
  { output.addToPrimaryMethod("  astore_1");
    output.decrementStackCount(1);
  }
}
