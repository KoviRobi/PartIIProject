package rmk35.partIIProject.frontend;

public class SchemeParserException extends RuntimeException
{ static final long serialVersionUID = 0;
  String file;
  long line;
  long character;
  String message;

  public SchemeParserException(String m, String f, long l, long c)
  { message = m;
    file = f;
    line = l;
    character = c;
  }

  @Override
  public String toString()
  { return "Parse exception at " + file + ":" + line + ":" + character + "\n" + message;
  }
}
