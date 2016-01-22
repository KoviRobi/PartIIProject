package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.SyntaxErrorException;

import java.util.List;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

public class SchemeVector implements SchemeLiteral
{ Object[] data;
  String file;
  long line;
  long character;

  public SchemeVector(Object[] data, String file, long line, long character)
  { this.data = data;
    this.file = file;
    this.line = line;
    this.character = character;
  }

  @Override
  public boolean mutable()
  { return true;
  }

  public String toString()
  { StringBuilder sb = new StringBuilder();
    sb.append("#(");
    for (Object o : data)
      sb.append(o.toString() + " ");
    sb.append(")");
    return sb.toString();
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  public String file() { return file; }
  public long line() { return line; }
  public long character() { return character; }
}
