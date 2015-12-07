package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.frontend.SchemeParserException;

public class SchemeString extends SchemeEquality implements SchemeObject
{ public boolean mutable() { return false; }

  String value;

  public SchemeString(String text, String file, long line, long character)
  { StringBuilder sb = new StringBuilder();
    value = sb.toString();
  }

  public boolean eqv(Object other)
  { if (this.eq(other))
    { return true;
    } else if (other instanceof SchemeString)
    { return value.equals((SchemeString)other);
    } else
    { return false;
    }
  }

  public boolean equal(Object other)
  { return eqv(other);
  }

  public String toString()
  { return value;
  }
}
