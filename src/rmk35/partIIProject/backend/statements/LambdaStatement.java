package rmk35.partIIProject.backend.statements;

import java.util.List;
import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.InnerClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

public class LambdaStatement extends Statement
{ List<IdentifierValue> formals;
  List<IdentifierStatement> closureVariables;
  Statement body;

  public LambdaStatement(List<IdentifierValue> formals, List<IdentifierStatement> closureVariables, Statement body)
  { this.formals = formals;
    this.closureVariables = closureVariables;
    this.body = body;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { output.addToPrimaryMethod("  ; LambdaStatement\n");
    String innerClassName = output.uniqueID() + "$Lambda";
    InnerClass innerClass = new InnerClass(innerClassName, closureVariables, output.getMainClass(), formals.size());
    body.generateOutput(definitions, macros, innerClass);
    output.getMainClass().addInnerClass(innerClass);

    output.addToPrimaryMethod("  new " + innerClassName + "\n"); // Create class
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  dup\n"); // For invokenonvirtual, need 'this' pointer
    output.incrementStackCount(1);
    // FIXME: Pass in closure variables here to the constructor
    innerClass.invokeConstructor(definitions, macros, output);
    // FIXME: delete output.addToPrimaryMethod("  invokenonvirtual " + innerClassName + "/<init>()V\n");
    output.decrementStackCount(1);
    output.addToPrimaryMethod("\n");
  }
}
