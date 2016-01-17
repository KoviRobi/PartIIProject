package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

public class SchemeAbbreviation implements SchemeLiteral
{ String value;
  String file;
  long line;
  long character;

  public SchemeAbbreviation(String prefix, Object datum, String file, long line, long character)
  { value = prefix + datum.toString();
    this.file = file;
    this.line = line;
    this.character = character;
  }

  public boolean mutable()
  { return false;
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
