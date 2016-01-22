package rmk35.partIIProject.backend;

import java.util.List;
import rmk35.partIIProject.backend.statements.Statement;

public class JavaBytecodeGenerator
{ public static OutputClass generateOutput(String name, List<Statement> statements)
  { OutputClass output = new MainClass(name);

    for (Statement statement : statements)
    { statement.generateOutput(output);
    }

    return output;
  }

// FIXME: Make this a proper test
  public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, java.io.IOException
  {
  }
}
