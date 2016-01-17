package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

public class SchemeBoolean implements SchemeLiteral
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
