package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

public class SchemeIdentifier implements AST
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
  { return identifier; // This may change, hence separate getData
  }

  @Override
  public Statement accept(ASTVisitor visitor)
  { return visitor.visit(this);
  }

  public String getData()
  { return identifier;
  }
}
