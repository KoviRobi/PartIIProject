package rmk35.partIIProject.backend;

public class InternalCompilerException extends RuntimeException
{ String message;

  public InternalCompilerException(String message)
  { this.message = message;
  }
}