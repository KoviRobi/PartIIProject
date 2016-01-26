package rmk35.partIIProject.backend;

import java.util.List;
import rmk35.partIIProject.backend.statements.Statement;

public class JavaByteCodeGenerator
{ public static OutputClass generateOutput(MainClass mainClass, List<Statement> statements)
  { for (Statement statement : statements)
    { statement.generateOutput(mainClass, mainClass, mainClass.getPrimaryMethod());
    }

    return mainClass;
  }

// FIXME: Make this a proper test
  public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, java.io.IOException
  {
  }
}
