package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

public class SchemeBytevector implements SchemeLiteral
{ Object[] value;
  String file;
  long line;
  long character;

  public SchemeBytevector(Object[] text, String file, long line, long character)
  { value = text;
    this.file = file;
    this.line = line;
    this.character = character;
  }

  @Override
  public boolean mutable()
  { return true;
  }

  public String toString()
  { return value.toString();
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  public String file() { return file; }
  public long line() { return line; }
  public long character() { return character; }
}
