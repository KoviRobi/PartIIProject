package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.SyntaxErrorException;

import java.math.BigDecimal;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

public class SchemeSimpleNumber extends SchemeNumber
{ BigDecimal bigdec;
  String file;
  long line;
  long character;

  public boolean mutable() { return false; }

  public SchemeSimpleNumber(String text, String file, long line, long character)
  { bigdec = new BigDecimal(text);
    this.file = file;
    this.line = line;
    this.character = character;
  }

  public boolean eqv(Object other)
  { return other instanceof SchemeSimpleNumber?
           bigdec.equals(((SchemeSimpleNumber)other).bigdec):false;
  }
  
  public String toString()
  { return bigdec.toString();
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  public String file() { return file; }
  public long line() { return line; }
  public long character() { return character; }
}
