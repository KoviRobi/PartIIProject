package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

public class SchemeLabelledData implements SchemeObject
{ String value;
  
  public SchemeLabelledData(String label, Object datum, String file, long line, long character)
  { if (datum == null)
    { value = label + "Null datum!";
    } else
    { value = label + datum;
    }
  }

  public boolean mutable()
  { return false;
  }

  public boolean eqv(Object other)
  { return other instanceof SchemeIdentifier?
           true:false;
  }
  
  public boolean equal(Object other)
  { return eqv(other);
  }

  public String toString()
  { return value;
  }

  @Override
  public Statement accept(ASTVisitor visitor)
  { return visitor.visit(this);
  }
}
