package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

public class SchemeBoolean extends SchemeEquality implements SchemeObject
{ public boolean mutable() { return false; }

  boolean value;
  String file;
  long line;
  long character;

  public SchemeBoolean(String text, String file, long line, long character) throws SyntaxErrorException
  { if (text.equals("#f") || text.equals("#false"))
    { value = false;
    } else if (text.equals("#t") || text.equals("#true"))
    { value = true;
    } else
    { throw new SyntaxErrorException("Failed to parse \"" + text + "\"", file, line, character);
    }
    this.file = file;
    this.line = line;
    this.character = character;
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
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  public String file() { return file; }
  public long line() { return line; }
  public long character() { return character; }
}
