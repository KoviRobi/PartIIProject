package rmk35.partIIProject.backend;

import java.util.Map;

public class ClosureIdentifierStatement extends Statement
{ IdentifierValue value;

  public ClosureIdentifierStatement(IdentifierValue value)
  { this.value = value;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { throw new UnsupportedOperationException();
  }
}
