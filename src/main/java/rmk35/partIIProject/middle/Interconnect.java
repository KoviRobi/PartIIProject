package rmk35.partIIProject.middle;

import rmk35.partIIProject.frontend.SchemeParser;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.backend.statements.Statement;

import java.util.List;
import java.util.ArrayList;

public class Interconnect
{ Environment environment;

  public Interconnect(Environment initialEnvironment)
  { this.environment = initialEnvironment;
  }

  // REPL
  public Statement ASTToStatement(RuntimeValue data)
  { return data.accept(new ASTConvertVisitor(environment));
  }

  public Environment getInteractionEnvironment()
  { return environment;
  }

  // Compiler
  public List<Statement> ASTsToStatements(List<RuntimeValue> datum)
  { List<Statement> returnValue = new ArrayList<>(datum.size());
    ASTConvertVisitor visitor = new ASTConvertVisitor(environment);

    for (RuntimeValue ast : datum)
    { // Holds state of the environment
      returnValue.add(ast.accept(visitor));
    }

    return returnValue;
  }
}
