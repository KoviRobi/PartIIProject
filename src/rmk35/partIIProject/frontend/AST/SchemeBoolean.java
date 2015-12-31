package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

import rmk35.partIIProject.frontend.SchemeParserException;

public class SchemeBoolean extends SchemeEquality implements SchemeObject
{ public boolean mutable() { return false; }

  boolean value;

  public SchemeBoolean(String text, String file, long line, long character)
  { if (text.equals("#f") || text.equals("#false"))
    { value = false;
    } else if (text.equals("#t") || text.equals("#true"))
    { value = true;
    } else
    { throw new SchemeParserException("Failed to parse \"" + text + "\"", file, line, character);
    }
  }

  public boolean eqv(Object other)
  { if (this.eq(other))
    { return true;
    } else if (other instanceof SchemeBoolean)
    { return value == ((SchemeBoolean)other).value;
    } else
    { return false;
    }
  }

  public boolean equal(Object other)
  { return eqv(other);
  }

  public String toString()
  { return value?"#true":"#false";
  }

  @Override
  public Statement accept(ASTVisitor visitor)
  { return visitor.visit(this);
  }
}
