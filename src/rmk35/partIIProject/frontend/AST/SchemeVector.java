package rmk35.partIIProject.frontend.AST;

import java.util.List;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

public class SchemeVector implements SchemeObject
{ Object[] data;
  public SchemeVector(Object[] data, String file, long line, long character)
  { this.data = data;
  }

  @Override
  public boolean mutable()
  { return true;
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
    sb.append(" #( ");
    for (Object o : data)
      sb.append(o.toString() + " ");
    sb.append(") ");
    return sb.toString();
  }

  @Override
  public Statement accept(ASTVisitor visitor)
  { return visitor.visit(this);
  }
}
