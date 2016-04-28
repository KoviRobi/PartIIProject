package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn.Substitution;

public class ASTLiteralIdentifierMatchVisitor extends ASTNoMatchVisitor
{ EnvironmentValue definitionEnvironment;
  String parameter;

  public ASTLiteralIdentifierMatchVisitor(EnvironmentValue definitionEnvironment, IdentifierValue parameter)
  { this.definitionEnvironment = definitionEnvironment;
    this.parameter = parameter.getValue();
  }

  @Override
  public Substitution visit(IdentifierValue argument)
  { // Using lookUpSilent as we may be matching against unbound identifiers, e.g. "=>" in cond
    if (sameBinding(parameter, argument.getValue()) && parameter.equals(argument.getValue()))
    { return  new Substitution();
    } else
    { return null;
    }
  }

  boolean sameBinding(String inDefinitionEnvironment, String inUseEnvironment)
  { return
      (definitionEnvironment.getOrNull(inDefinitionEnvironment) == null &&
       getUseEnvironment().getOrNull(inUseEnvironment) == null) ||
      (definitionEnvironment.getOrNull(inDefinitionEnvironment) != null &&
       definitionEnvironment.getOrNull(inDefinitionEnvironment).equals(getUseEnvironment().getOrNull(inUseEnvironment)))
      ;
  }

  @Override
  public String toString()
  { return parameter;
  }
}
