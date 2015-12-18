package rmk35.partIIProject.backend;

import java.util.Map;

public abstract class IdentifierStatement extends Statement
{ /* Assumes variable to set to is on top of the stack */
  public abstract void generateSetOutput(Map<IdentifierValue, Definition> definitions,
                                         Map<IdentifierValue, Macro> macros,
                                         OutputClass output);
}
