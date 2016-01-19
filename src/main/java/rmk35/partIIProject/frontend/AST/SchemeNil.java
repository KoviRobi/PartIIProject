package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

import java.util.List;

public class SchemeNil implements AST
{ String file;
  long line;
  long character;

  public SchemeNil(String file, long line, long character)
  { this.file = file;
    this.line = line;
    this.character = character;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  public String file() { return file; }
  public long line() { return line; }
  public long character() { return character; }
}
