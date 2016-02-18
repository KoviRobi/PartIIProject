package rmk35.partIIProject.backend;

import java.util.List;
import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.BeginStatement;

public class JavaByteCodeGenerator
{ public static OutputClass generateOutput(MainClass mainClass, List<Statement> statements)
  { (new BeginStatement(statements)).generateOutput(mainClass, mainClass, mainClass.getPrimaryMethod());
    return mainClass;
  }
}
