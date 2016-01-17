package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

// ToDo: may be useful to actually do labels
public class SchemeLabelReference implements AST
{ String label;
  String file;
  long line;
  long character;

  public SchemeLabelReference(String label)
  { this.label = label;
    this.file = file;
    this.line = line;
    this.character = character;
  }

  public String toString()
  { return label;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  public String file() { return file; }
  public long line() { return line; }
  public long character() { return character; }
}
