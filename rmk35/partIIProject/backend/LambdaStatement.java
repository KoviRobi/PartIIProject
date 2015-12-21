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
                             OutputClass output)
  { String innerClassName = output.uniqueID() + "$Lambda";
    InnerClass innerClass = new InnerClass(innerClassName, closureVariables);
    body.generateOutput(definitions, macros, innerClass);
    output.addToPrimaryMethod("  new " + innerClassName + "\n"); // Create class
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  dup\n"); // For invokenonvirtual, need 'this' pointer
    output.incrementStackCount(1);
    // FIXME: Pass in closure variables here to the constructor
    output.addToPrimaryMethod("  invokenonvirtual " + innerClassName + "()V\n");
    output.incrementStackCount(1);
  }
}
