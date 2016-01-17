package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

public class SchemeIdentifier implements AST
{ String identifier;
  String file;
  long line;
  long character;
 
  public SchemeIdentifier(String text, String file, long line, long character)
  { identifier = text;
    this.file = file;
    this.line = line;
    this.character = character;
  }

  public String toString()
  { return identifier; // This may change, hence separate getData
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  public String getData()
  { return identifier;
  }

  public String file() { return file; }
  public long line() { return line; }
  public long character() { return character; }
}
