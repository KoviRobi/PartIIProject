package rmk35.partIIProject.backend;

import java.util.Map;

public class IntegerConstantStatement extends Statement
{ Integer value;

  public IntegerConstantStatement(Integer value)
  { this.value = value;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                                      Map<IdentifierValue, Macro> macros,
                                      OutputClass output)
  { output.addToPrimaryMethod("  new  rmk35/partIIProject/backend/NumberValue\n");
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  dup\n");
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  ldc " + value + "\n");
    output.incrementStackCount(1);
    output.addToPrimaryMethod("  invokenonvirtual rmk35/partIIProject/backend/NumberValue/<init>(I)V\n");
    output.decrementStackCount(2);
  }
}
