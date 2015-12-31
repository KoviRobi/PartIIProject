package rmk35.partIIProject.backend.statements;

import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

import lombok.ToString;

@ToString
public class SetStatement extends Statement
{ IdentifierStatement variable;
  Statement value;

  public SetStatement(IdentifierStatement variable, Statement value)
  { this.variable = variable;
    this.value = value;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { output.addToPrimaryMethod("  ; SetStatement\n");
    if (value == null)
    { output.addToPrimaryMethod("  aconst_null\n");
    } else
    { value.generateOutput(definitions, macros, output);
    }
    output.incrementStackCount(1);
    variable.generateSetOutput(definitions, macros, output); // Decrements stack count
    output.addToPrimaryMethod("\n");
  }
}
