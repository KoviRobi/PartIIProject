package rmk35.partIIProject.backend;

import java.util.List;
import java.util.Map;

public class LambdaStatement extends Statement
{ // FIXME: Single formal at the moment
  Identifier formals;
  List<Identifier> closureVariables;
  Statement body;

  public void generateOutput(Map<Identifier, Definition> definitions,
                             Map<Identifier, Macro> macros,
                             OutputClass outputClass)
  { InnerClass innerClass = new InnerClass(outputClass.uniqueID() + "Lambda", closureVariables);
    body.generateOutput(definitions, macros, innerClass);
  }
}
