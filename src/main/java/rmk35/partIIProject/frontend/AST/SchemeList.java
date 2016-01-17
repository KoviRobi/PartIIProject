package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

import java.util.List;

public class SchemeList implements AST
{ List<AST> data;
  String file;
  long line;
  long character;

  public SchemeList(List<AST> data, String file, long line, long character)
  { this.data = data;
    this.file = file;
    this.line = line;
    this.character = character;
  }

  public String toString()
  { StringBuilder sb = new StringBuilder();
    sb.append(" ( ");
    for (AST o : data)
    { if (o != null)
      { sb.append(o.toString() + " ");
      } else
      { sb.append("'() ");
      }
    }

    sb.append(") ");
    return sb.toString();
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  public List<AST> getData()
  { return data;
  }

  public String file() { return file; }
  public long line() { return line; }
  public long character() { return character; }
}
