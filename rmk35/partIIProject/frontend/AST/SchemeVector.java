package rmk35.partIIProject.frontend.AST;

import java.util.List;

public class SchemeVector
{ Object[] data;
  public SchemeVector(Object[] data, String file, long line, long character)
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
    sb.append(" #( ");
    for (Object o : data)
      sb.append(o.toString() + " ");
    sb.append(") ");
    return sb.toString();
  }
}
