package rmk35.partIIProject.backend.statements;

import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;

public abstract class IdentifierStatement extends Statement
{ /* Assumes variable to set to is on top of the stack */
  public abstract void generateSetOutput(OutputClass output);
  public abstract String getName();
}
