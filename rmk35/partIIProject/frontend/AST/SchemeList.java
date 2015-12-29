package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

import java.util.List;

public class SchemeList implements AST
{ List<Object> data;
  public SchemeList(List<Object> data, String file, long line, long character)
  { this.data = data;
  }

  public boolean eqv(Object other)
  { return other instanceof SchemeIdentifier?
           true:false;
  }
  
  public boolean equal(Object other)
  { return eqv(other);
  }

  public String toString()
  { StringBuilder sb = new StringBuilder();
    sb.append(" ( ");
    for (Object o : data)
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
  public Statement accept(ASTVisitor visitor)
  { return visitor.visit(this);
  }
}
