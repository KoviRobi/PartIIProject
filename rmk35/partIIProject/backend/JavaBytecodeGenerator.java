package rmk35.partIIProject.backend;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class JavaBytecodeGenerator
{ public static OutputClass generateOutput(String fileName, List<Statement> statements, IdentifierFactory identifiers)
  { Map<IdentifierValue, Definition> definitions = new HashMap<>();
    Map<IdentifierValue, Macro> macros = new HashMap<>();
    OutputClass output = new MainClass(fileName);

    for (Statement statement : statements)
    { statement.generateOutput(definitions, macros, output);
    }

    return output;
  }

  public static void main(String[] args)
  { List<Statement> statements = new ArrayList<>();
    IdentifierFactory identifiers = new IdentifierFactory();

    statements.add
      (new LambdaStatement
        ( identifiers.getIdentifier("x")
        , new ArrayList<>()
        , new LocalIdentifierStatement(identifiers.getIdentifier("x"))));

    System.out.println(generateOutput("test", statements, identifiers).toString());
  }
}
