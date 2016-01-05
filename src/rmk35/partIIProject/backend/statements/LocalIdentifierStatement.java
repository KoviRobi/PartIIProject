package rmk35.partIIProject.backend.statements;

import java.util.Collection;
import java.util.TreeSet;
import java.util.Map;
import rmk35.partIIProject.backend.Macro;
import rmk35.partIIProject.backend.Definition;
import rmk35.partIIProject.backend.OutputClass;

import lombok.ToString;

@ToString
public class LocalIdentifierStatement extends IdentifierStatement
{ String name;
  int localIndex;

  public LocalIdentifierStatement(String name, int localIndex)
  { this.name = name;
    this.localIndex = localIndex;
  }

  @Override
  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod("  ; LocalIdentifier Get\n");
    if (localIndex < 4)
    { output.addToPrimaryMethod("  aload_" + localIndex + "\n");
    } else
    { output.addToPrimaryMethod("  aload " + localIndex + "\n");
    }
    output.incrementStackCount(1);
    output.addToPrimaryMethod("\n");
  }

  /* Assumes variable to set to is on top of the stack */
  @Override
  public void generateSetOutput(OutputClass output)
  { output.addToPrimaryMethod("  ; LocalIdentifier Set\n");
    if (localIndex < 4)
    { output.addToPrimaryMethod("  astore_" + localIndex + "\n");
    } else
    { output.addToPrimaryMethod("  astore " + localIndex + "\n");
    }
    output.decrementStackCount(1);
    output.addToPrimaryMethod("\n");
  }

  @Override
  public String getName()
  { return name;
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
