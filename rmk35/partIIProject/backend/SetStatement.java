package rmk35.partIIProject.backend;

import java.util.Map;

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
  { if (value == null)
    { output.addToPrimaryMethod("  aconst_null\n");
    } else
    { value.generateOutput(definitions, macros, output);
    }
    output.incrementStackCount(1);
    variable.generateSetOutput(definitions, macros, output);
    output.decrementStackCount(1);
  }
}
