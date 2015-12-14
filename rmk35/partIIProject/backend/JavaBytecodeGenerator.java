package rmk35.partIIProject.backend;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class JavaBytecodeGenerator
{ public static void generateCode(List<Statement> statements)
  { Map<Identifier, Definition> definitions = new HashMap<>();
    Map<Identifier, Macro> macros = new HashMap<>();

    for (Statement statement : statements)
    { statement.generateCode(definitions, macros);
    }
  }
}
