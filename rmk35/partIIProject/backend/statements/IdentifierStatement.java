package rmk35.partIIProject.backend.statements;

import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

public abstract class IdentifierStatement extends Statement
{ /* Assumes variable to set to is on top of the stack */
  public abstract void generateSetOutput(Map<IdentifierValue, Definition> definitions,
                                         Map<IdentifierValue, Macro> macros,
                                         OutputClass output);
}
