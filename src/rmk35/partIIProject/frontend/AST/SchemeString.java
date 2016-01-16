package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

public class SchemeString extends SchemeEquality implements SchemeObject
{ public boolean mutable() { return false; }

  String value;
  String file;
  long line;
  long character;

  public SchemeString(String text, String file, long line, long character)
  { StringBuilder sb = new StringBuilder(text);
    value = sb.toString();
    this.file = file;
    this.line = line;
    this.character = character;
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

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  public String file() { return file; }
  public long line() { return line; }
  public long character() { return character; }
}
