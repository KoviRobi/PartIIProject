package rmk35.partIIProject.backend.statements;

import java.util.Collection;
import java.util.TreeSet;
import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;

import lombok.ToString;

@ToString
public class SetStatement extends Statement
{ IdentifierStatement variable;
  Statement value;

  public SetStatement(IdentifierStatement variable, Statement value)
  { this.variable = variable;
    this.value = value;
  }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod("  ; SetStatement\n");
    if (value == null)
    { output.addToPrimaryMethod("  aconst_null\n");
    } else
    { value.generateOutput(output);
    }
    output.incrementStackCount(1);
    variable.generateSetOutput(output); // Decrements stack count
    output.addToPrimaryMethod("\n");
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
