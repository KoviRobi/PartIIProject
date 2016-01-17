package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

public class SchemeCharacter implements SchemeObject
{ public boolean mutable() { return false; }

  char value;
  String file;
  long line;
  long character;

  public SchemeCharacter(String text, String file, long line, long character) throws SyntaxErrorException
  { if (text.length() == 3)
    { value = text.charAt(3);
    } else if (text.startsWith("#\\x"))
    { value = Character.valueOf((char)Integer.parseInt(text.substring(3), 16));
    } else if (text.equals("#\\alarm"))
    { value = '\u0008';
    } else if (text.equals("#\\backspace"))
    { value = '\b';
    } else if (text.equals("#\\delete"))
    { value = '\u007F';
    } else if (text.equals("#\\escape"))
    { value = '\u001B';
    } else if (text.equals("#\\newline"))
    { value = '\n';
    } else if (text.equals("#\\null"))
    { value = '\0';
    } else if (text.equals("#\\return"))
    { value = '\r';
    } else if (text.equals("#\\space"))
    { value = ' ';
    } else if (text.equals("#\\tab"))
    { value = '\t';
    } else
    { throw new SyntaxErrorException("Failed to parse \"" + text + "\"", file, line, character);
    }
    this.file = file;
    this.line = line;
    this.character = character;
  }

  public String toString()
  { return "#\\" + Character.toString(value);
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  public String file() { return file; }
  public long line() { return line; }
  public long character() { return character; }
}
