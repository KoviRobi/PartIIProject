package rmk35.partIIProject.frontend.AST;

public class SchemeAbbreviation
{ String value;
  
  public SchemeAbbreviation(String prefix, Object datum, String file, long line, long character)
  { value = prefix + datum.toString();
  }

  public boolean eqv(Object other)
  { return other instanceof SchemeIdentifier?
           true:false;
  }
  
  public boolean equal(Object other)
  { return eqv(other);
  }

  public String toString()
  { return value;
  }
}
