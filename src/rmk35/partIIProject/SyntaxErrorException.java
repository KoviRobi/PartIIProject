package rmk35.partIIProject;

import lombok.Value;

@Value
public class SyntaxErrorException extends RuntimeException // Antlr does not handle checked exceptions
{ String message;
  String file;
  long line;
  long character;
}