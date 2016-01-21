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
  { List<Statement> statements = new ArrayList<>();

/*    Statement idStatement = new LambdaStatement
        ( Arrays.asList(new String[] {"x"})
        , new ArrayList<IdentifierStatement>()
        , new LocalIdentifierStatement("x", 1));
    Statement print42 = new JavaCallStatement(new NativeFieldStatement("java.lang.System", "out"), "println", new RuntimeValueStatement("42", NumberValue.class, new String[] {"I"}));
    Statement print7 = new JavaCallStatement(new NativeFieldStatement("java.lang.System", "out"), "println", new ApplicationStatement(idStatement, new RuntimeValueStatement("7", NumberValue.class, new String[] {"I"})));

    statements.add(new ApplicationStatement(new LambdaStatement(Arrays.asList(new String[] {"input"}), new ArrayList<IdentifierStatement>(), print42), new LambdaStatement(Arrays.asList(new String[] {"input"}), new ArrayList<IdentifierStatement>(), print42)));

    statements.add(print7);

    IdentifierStatement fooIdentifier = new GlobalIdentifierStatement("foo");
    Statement condBasedOnFoo = new LambdaStatement(Arrays.asList(new String[] {"input"}), new ArrayList<IdentifierStatement>(), new IfStatement(fooIdentifier, print7, print42));

    statements.add(new SetStatement(fooIdentifier, new RuntimeValueStatement("0", BooleanValue.class, new String[] {"Z"})));

    statements.add(new ApplicationStatement(condBasedOnFoo, idStatement));

    statements.add(new SetStatement(fooIdentifier, new RuntimeValueStatement("1", BooleanValue.class, new String[] {"Z"})));
    statements.add(new ApplicationStatement(condBasedOnFoo, idStatement));

    // TODO: Closure variables
    Statement closure = new LambdaStatement
    (Arrays.asList(new String[] {"x"})
    ,new ArrayList<IdentifierStatement>()
    ,new LambdaStatement
      (Arrays.asList(new String[] {"y"})
      ,Arrays.asList(new IdentifierStatement[] {new LocalIdentifierStatement("x", 1)})
      ,new ClosureIdentifierStatement("x")));

    statements.add(new JavaCallStatement(new NativeFieldStatement("java.lang.System", "out"), "println", new ApplicationStatement(new ApplicationStatement(closure, new RuntimeValueStatement("42", NumberValue.class, new String[] {"I"})), new RuntimeValueStatement("7", NumberValue.class, new String[] {"I"}))));
*/
    Statement systemOut = new JavaStaticFieldStatement(new StringValueStatement("java.lang.System"), new StringValueStatement("out"));
    statements.add
    (new JavaCallStatement
      (new JavaMethodStatement(systemOut, new StringValueStatement("println"), new StringValueStatement("java.lang.Object"))
      , systemOut
      , new ApplicationStatement
        (new LambdaStatement
          ( Arrays.asList(new String[] {"x", "y"})
          , new ArrayList<IdentifierStatement>()
          , new LocalIdentifierStatement("y", 2))
        , new NumberValueStatement(3)
        , new NumberValueStatement(4))));
    generateOutput("test", statements).saveToDisk();
  }
}
