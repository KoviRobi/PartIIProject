package rmk35.partIIProject.backend;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import rmk35.partIIProject.backend.statements.*;  // FIXME
import rmk35.partIIProject.backend.runtimeValues.*; // FIXME

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
