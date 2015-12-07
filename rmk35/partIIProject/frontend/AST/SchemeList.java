package rmk35.partIIProject.frontend.AST;

import java.util.List;

public class SchemeList
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
  { return data.toString();
  }
}
