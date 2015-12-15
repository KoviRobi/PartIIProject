package rmk35.partIIProject.backend;

import java.util.Map;

public class GlobalIdentifierStatement extends Statement
{ IdentifierValue value;

  public GlobalIdentifierStatement(IdentifierValue value)
  { this.value = value;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { throw new UnsupportedOperationException();
  }
}
