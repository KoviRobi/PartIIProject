package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.frontend.SchemeParserException;

public class SchemeCharacter extends SchemeEquality implements SchemeObject
{ public boolean mutable() { return false; }

  char value;

  public SchemeCharacter(String text, String file, long line, long character)
  { if (text.length() == 3)
    { value = text.charAt(3);
    } else if (text.startsWith("#\\x"))
    { value = Character.valueOf((char)Integer.parseInt(text.substring(3), 16));
    } else if (text.equals("#\\alarm"))
    { value = '\u0008';
    } else if (text.equals("#\\backspace"))
    { value = '\b';
    } else if (text.equals("#\\delete"))
    { value = '\u007F';
    } else if (text.equals("#\\escape"))
    { value = '\u001B';
    } else if (text.equals("#\\newline"))
    { value = '\n';
    } else if (text.equals("#\\null"))
    { value = '\0';
    } else if (text.equals("#\\return"))
    { value = '\r';
    } else if (text.equals("#\\space"))
    { value = ' ';
    } else if (text.equals("#\\tab"))
    { value = '\t';
    } else
    { throw new SchemeParserException("Failed to parse \"" + text + "\"", file, line, character);
    }
  }

  public boolean eqv(Object other)
  { if (this.eq(other))
    { return true;
    } else if (other instanceof SchemeCharacter)
    { return value == ((SchemeCharacter)other).value;
    } else
    { return false;
    }
  }

  public boolean equal(Object other)
  { return eqv(other);
  }

  public String toString()
  { return "#\\" + Character.toString(value);
  }
}
