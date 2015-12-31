package rmk35.partIIProject.frontend.AST;

public class SchemeLabelledData
{ String value;
  
  public SchemeLabelledData(String label, Object datum, String file, long line, long character)
  { if (datum == null)
    { value = label + "Null datum!";
    } else
    { value = label + datum;
    }
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
