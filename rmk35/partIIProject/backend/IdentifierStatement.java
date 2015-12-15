package rmk35.partIIProject.backend;

import java.util.Map;

public class IdentifierStatement extends Statement
{ IdentifierValue value;

  public enum Type
  { Global,
    Local,
    Closure
  }

  Type type;

  public IdentifierStatement(IdentifierValue value, Type type)
  { this.value = value;
    this.type = type;
  }

  public void generateOutput(Map<IdentifierValue, Definition> definitions,
                             Map<IdentifierValue, Macro> macros,
                             OutputClass output)
  { switch (type)
    { case Global:
        output.addToPrimaryMethod("  ");
        break;
      case Local:
        output.addToPrimaryMethod("  aload_1");
        break;
      case Closure:
        output.addToPrimaryMethod("  ");
        break;
    }
  }
}
