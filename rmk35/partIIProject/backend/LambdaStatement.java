package rmk35.partIIProject.backend;

import java.util.List;
import java.util.Map;

public class LambdaStatement extends Statement
{ // FIXME: Single formal at the moment
  Identifier formals;
  Statement body;
  public void generateOutput(Map<Identifier, Definition> definitions,
                             Map<Identifier, Macro> macros,
                             OutputClass outputClass)
  {
  }
}
