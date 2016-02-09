package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.ASTSyntaxSpecificationVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.UnspecifiedValueStatement;

import lombok.ToString;

@ToString
public class DefineSyntaxBinding extends SintacticBinding
{ @Override
  public Statement applicate(Environment environment, RuntimeValue operator, RuntimeValue operands)
  { Pair<String, SyntaxBinding> binding = operands.accept(new ASTSyntaxSpecificationVisitor(environment));
    environment.addBinding(binding.getFirst(), binding.getSecond());
    return new UnspecifiedValueStatement();
  }
}
