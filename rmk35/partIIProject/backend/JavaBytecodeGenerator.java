package rmk35.partIIProject.backend;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

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

  public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, ClassNotFoundException
  { List<Statement> statements = new ArrayList<>();
    IdentifierFactory identifiers = new IdentifierFactory();

    Statement idStatement = new LambdaStatement
        ( identifiers.getIdentifier("x")
        , new ArrayList<IdentifierValue>()
        , new LocalIdentifierStatement(identifiers.getIdentifier("x")));
    IdentifierStatement global = new GlobalIdentifierStatement(null, "test/foo", "Ljava/lang/String");
/*
    statements.add(idStatement);
    statements.add(new ApplicationStatement(idStatement, idStatement));
    statements.add(new IfStatement(idStatement, idStatement, idStatement));
    statements.add(new SetStatement(global, null));
*/
    statements.add(new IntegerConstantStatement(42));
    statements.add(new JavaCallStatement(new NativeFieldStatement("java.lang.System", "out"), "println", 1));
    System.out.println(generateOutput("test", statements, identifiers).toString());
  }
}
