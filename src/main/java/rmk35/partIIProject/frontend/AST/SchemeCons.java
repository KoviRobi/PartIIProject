package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

import java.util.List;

public class SchemeCons implements AST
{ AST car;
  AST cdr;
  String file;
  long line;
  long character;

  public SchemeCons(AST car, AST cdr, String file, long line, long character)
  { this.car = car;
    this.cdr = cdr;
    this.file = file;
    this.line = line;
    this.character = character;
  }

  public AST car()
  { return car;
  }

  public AST cdr()
  { return cdr;
  }

  public void setCar(AST car)
  { this.car = car;
  }

  public void setCdr(AST cdr)
  { this.cdr = cdr;
  }

  public String toString()
  { StringBuilder returnValue = new StringBuilder("(");
    returnValue.append(car());
    AST cell = cdr();
    while (cell instanceof SchemeCons)
    { returnValue.append(" ");
      returnValue.append(((SchemeCons)cell).car());
      cell = ((SchemeCons)cell).cdr();
    }

    if (! (cell instanceof SchemeNil))
    { returnValue.append(" . ");
      returnValue.append(cell);
    }
    returnValue.append(")");
    return returnValue.toString();
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  public String file() { return file; }
  public long line() { return line; }
  public long character() { return character; }
}
