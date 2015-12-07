package rmk35.partIIProject.frontend.AST;

public class SchemeIdentifier
{ String identifier;
  
  public SchemeIdentifier(String text, String file, long line, long character)
  { identifier = text;
  }

  public boolean eqv(Object other)
  { return other instanceof SchemeIdentifier?
           true:false;
  }
  
  public boolean equal(Object other)
  { return eqv(other);
  }

  public String toString()
  { return identifier;
  }
}
