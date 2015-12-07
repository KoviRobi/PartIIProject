package rmk35.partIIProject.frontend.AST;

import java.math.BigDecimal;

import rmk35.partIIProject.frontend.SchemeParserException;

public class SchemeSimpleNumber extends SchemeNumber
{ BigDecimal bigdec;

  public boolean mutable() { return false; }

  public SchemeSimpleNumber(String text, String file, long line, long character)
  { bigdec = new BigDecimal(text);
  }

  public boolean eqv(Object other)
  { return other instanceof SchemeSimpleNumber?
           bigdec.equals(((SchemeSimpleNumber)other).bigdec):false;
  }
  
  public boolean equal(Object other)
  { return eqv(other);
  }

  public String toString()
  { return bigdec.toString();
  }
}
