package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

public class SchemeLabelledData implements AST
{ String value;
  String file;
  long line;
  long character;

  public SchemeLabelledData(String label, Object datum, String file, long line, long character)
  { if (datum == null)
    { value = label + "Null datum!";
    } else
    { value = label + datum;
    }
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
