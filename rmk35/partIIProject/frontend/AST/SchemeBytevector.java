package rmk35.partIIProject.frontend.AST;

public class SchemeBytevector
{ public SchemeBytevector(String text, String file, long line, long character)
  {
  }

  public boolean eqv(Object other)
  { return other instanceof SchemeIdentifier?
           true:false;
  }
  
  public boolean equal(Object other)
  { return eqv(other);
  }

  public String toString()
  { throw new UnsupportedOperationException();
  }
}
