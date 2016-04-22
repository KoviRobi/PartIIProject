package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.UnspecifiedValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.ASTSyntaxSpecificationVisitor;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.RuntimeValueStatement;

import lombok.ToString;

@ToString
public class DefineSyntaxBinding extends SintacticBinding
{ @Override
  public Statement applicate(EnvironmentValue environment, OutputClass outputClass, MainClass mainClass, RuntimeValue operator, RuntimeValue operands)
  { Pair<String, SyntaxBinding> binding = operands.accept(new ASTSyntaxSpecificationVisitor(environment, mainClass));
    environment.addBinding(binding.getFirst(), binding.getSecond());
    return new RuntimeValueStatement(new UnspecifiedValue());
  }
}
