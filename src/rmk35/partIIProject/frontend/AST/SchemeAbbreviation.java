package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

public class SchemeAbbreviation implements SchemeObject
{ String value;
  
  public SchemeAbbreviation(String prefix, Object datum, String file, long line, long character)
  { value = prefix + datum.toString();
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
