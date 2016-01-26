package rmk35.partIIProject.backend.statements;

import java.util.Map;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;

public abstract class IdentifierStatement extends Statement
{ /* Assumes variable to set to is on top of the stack */
  public abstract void generateSetOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method);
  public abstract String getName();
}
