package rmk35.partIIProject.backend;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import rmk35.partIIProject.backend.statements.*;  // FIXME
import rmk35.partIIProject.backend.runtimeValues.*; // FIXME

public class JavaBytecodeGenerator
{ public static OutputClass generateOutput(String name, List<Statement> statements, IdentifierFactory identifiers)
  { Map<IdentifierValue, Definition> definitions = new HashMap<>();
    Map<IdentifierValue, Macro> macros = new HashMap<>();
    OutputClass output = new MainClass(name);

    for (Statement statement : statements)
    { statement.generateOutput(definitions, macros, output);
    }

    return output;
  }

  public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, java.io.IOException
  { List<Statement> statements = new ArrayList<>();
    IdentifierFactory identifiers = new IdentifierFactory();

    Statement idStatement = new LambdaStatement
        ( identifiers.getIdentifier("x")
        , new ArrayList<IdentifierValue>()
        , new LocalIdentifierStatement(identifiers.getIdentifier("x")));
    Statement print42 = new JavaCallStatement(new NativeFieldStatement("java.lang.System", "out"), "println", new IntegerConstantStatement(42));

    statements.add(new ApplicationStatement(new LambdaStatement(identifiers.getIdentifier("input"), new ArrayList<IdentifierValue>(), print42), new LambdaStatement(identifiers.getIdentifier("input"), new ArrayList<IdentifierValue>(), print42)));

    statements.add(new JavaCallStatement(new NativeFieldStatement("java.lang.System", "out"), "println", new ApplicationStatement(idStatement, new IntegerConstantStatement(43))));

    generateOutput("test", statements, identifiers).saveToDisk();
  }
}
