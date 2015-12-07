package rmk35.partIIProject.frontend.AST;

public class SchemeBytevector
{ String value;
  
  public SchemeBytevector(String text, String file, long line, long character)
  { value = text;
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
