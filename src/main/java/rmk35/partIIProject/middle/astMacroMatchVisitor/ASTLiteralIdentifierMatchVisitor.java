package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.frontend.AST.SchemeIdentifier;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;

import java.util.Map;
import java.util.Hashtable;

public class ASTLiteralIdentifierMatchVisitor extends ASTNoMatchVisitor
{ Environment definitionEnvironment;
  String parameter;

  public ASTLiteralIdentifierMatchVisitor(Environment definitionEnvironment, SchemeIdentifier parameter)
  { this.definitionEnvironment = definitionEnvironment;
    this.parameter = parameter.getData();
  }

  @Override
  public Map<String, AST> visit(SchemeIdentifier argument)
  { // Using lookUpSilent as we may be matching against unbound identifiers, e.g. "=>" in cond
    if (definitionEnvironment.lookUpSilent(parameter) ==
        useEnvironment.lookUpSilent(argument.getData()))
    { return  new Hashtable<>();
    } else
    { return null;
    }
  }
}
