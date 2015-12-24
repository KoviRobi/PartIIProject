package rmk35.partIIProject.backend.statements;

import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

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
