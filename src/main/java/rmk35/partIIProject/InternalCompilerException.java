package rmk35.partIIProject;

public class InternalCompilerException extends RuntimeException
{ String message;

  public InternalCompilerException(String message)
  { this.message = message;
  }

  @Override
  public String getMessage()
  { return message;
  }
}