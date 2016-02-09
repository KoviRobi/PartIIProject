package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.IdentifierValue;

import rmk35.partIIProject.middle.Environment;

import java.util.Map;
import java.util.Hashtable;

public class ASTLiteralIdentifierMatchVisitor extends ASTNoMatchVisitor
{ Environment definitionEnvironment;
  String parameter;

  public ASTLiteralIdentifierMatchVisitor(Environment definitionEnvironment, IdentifierValue parameter)
  { this.definitionEnvironment = definitionEnvironment;
    this.parameter = parameter.getValue();
  }

  @Override
  public Map<String, RuntimeValue> visit(IdentifierValue argument)
  { // Using lookUpSilent as we may be matching against unbound identifiers, e.g. "=>" in cond
    if (definitionEnvironment.lookUpSilent(parameter) ==
        useEnvironment.lookUpSilent(argument.getValue()))
    { return  new Hashtable<>();
    } else
    { return null;
    }
  }
}
