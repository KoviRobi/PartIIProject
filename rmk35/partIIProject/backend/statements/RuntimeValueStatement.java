package rmk35.partIIProject.backend.statements;

import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

public class RuntimeValueStatement extends Statement
{ String value;
  Class<?> type;
  String[] constructorArgumentTypes;

  public RuntimeValueStatement(String value, Class<?> type, String[] constructorArgumentTypes)
  { this.value = value;
    this.type = type;
    this.constructorArgumentTypes = constructorArgumentTypes;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                                      Map<IdentifierValue, Macro> macros,
                                      OutputClass output)
  { output.addToPrimaryMethod("  ; RuntimeValueStatement\n");
    output.addToPrimaryMethod("  new  rmk35/partIIProject/backend/runtimeValues/" + type.getSimpleName() + "\n");
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  dup\n");
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  ldc " + value + "\n");
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/" + type.getSimpleName() + "/<init>(" + String.join("", constructorArgumentTypes) + ")V\n");
    output.decrementStackCount(1 + constructorArgumentTypes.length);
    output.addToPrimaryMethod("\n");
  }
}
