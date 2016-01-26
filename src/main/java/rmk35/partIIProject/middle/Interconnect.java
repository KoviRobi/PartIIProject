package rmk35.partIIProject.middle;

import rmk35.partIIProject.frontend.SchemeParser;

import rmk35.partIIProject.backend.runtimeValues.*;
import rmk35.partIIProject.backend.statements.*;

import java.util.List;
import java.util.ArrayList;

public class Interconnect
{ Environment initialEnvironment;

  public Interconnect(Environment initialEnvironment)
  { this.initialEnvironment = initialEnvironment;
  }

  public List<Statement> ASTsToStatements(List<AST> datum)
  { List<Statement> returnValue = new ArrayList<>(datum.size());
    ASTConvertVisitor visitor = new ASTConvertVisitor(initialEnvironment);

    for (AST ast : datum)
    { // Holds state of the environment
      returnValue.add(ast.accept(visitor));
    }

    return returnValue;
  }
}
