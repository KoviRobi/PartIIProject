package rmk35.partIIProject.backend.statements;

import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

public class IntegerConstantStatement extends Statement
{ Integer value;

  public IntegerConstantStatement(Integer value)
  { this.value = value;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                                      Map<IdentifierValue, Macro> macros,
                                      OutputClass output)
  { output.addToPrimaryMethod("  new  rmk35/partIIProject/backend/runtimeValues/NumberValue\n");
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  dup\n");
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  ldc " + value + "\n");
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/NumberValue/<init>(I)V\n");
    output.decrementStackCount(2);
  }
}
