package rmk35.partIIProject.frontend.AST;

import java.math.BigDecimal;

import rmk35.partIIProject.frontend.SchemeParserException;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

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

  @Override
  public Statement accept(ASTVisitor visitor)
  { return visitor.visit(this);
  }
}
