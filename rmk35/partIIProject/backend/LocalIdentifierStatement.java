package rmk35.partIIProject.backend;

import java.util.Map;

public class LocalIdentifierStatement extends Statement
{ IdentifierValue value;

  public LocalIdentifierStatement(IdentifierValue value)
  { this.value = value;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { output.addToPrimaryMethod("  aload_1");
  }
}
