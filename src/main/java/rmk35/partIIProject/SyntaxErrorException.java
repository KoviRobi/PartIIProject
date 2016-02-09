package rmk35.partIIProject;

import rmk35.partIIProject.frontend.SourceInfo;

import lombok.Value;

@Value
public class SyntaxErrorException extends RuntimeException // Antlr does not handle checked exceptions
{ String message;
  SourceInfo sourceInfo;

  public SyntaxErrorException(String message, SourceInfo sourceInfo)
  { this.message = message;
    this.sourceInfo = sourceInfo;
  }

  public String getMessage()
  { return message;
  }
}