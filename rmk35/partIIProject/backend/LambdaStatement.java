package rmk35.partIIProject.backend;

import java.util.List;
import java.util.Map;

public class LambdaStatement extends Statement
{ // FIXME: Single formal at the moment
  IdentifierValue formals;
  List<IdentifierValue> closureVariables;
  Statement body;

  public LambdaStatement(IdentifierValue formals, List<IdentifierValue> closureVariables, Statement body)
  { this.formals = formals;
    this.closureVariables = closureVariables;
    this.body = body;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass outputClass)
  { InnerClass innerClass = new InnerClass(outputClass.uniqueID() + "Lambda", closureVariables);
    body.generateOutput(definitions, macros, innerClass);
  }
}
