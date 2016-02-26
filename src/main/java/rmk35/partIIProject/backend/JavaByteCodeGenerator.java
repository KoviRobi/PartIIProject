package rmk35.partIIProject.backend;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.BeginStatement;

import java.util.List;

public class JavaByteCodeGenerator
{ public static OutputClass generateOutput(MainClass mainClass, List<Statement> statements)
  { (new BeginStatement(statements)).generateOutput(mainClass, mainClass, mainClass.getPrimaryMethod());
    return mainClass;
  }
}
