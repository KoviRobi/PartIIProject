package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.Environment;

import rmk35.partIIProject.backend.statements.Statement;

import lombok.ToString;

@ToString
public class QuoteBinding extends SintacticBinding
{ @Override
  public Statement applicate(Environment useEnvironment, RuntimeValue operator, RuntimeValue operands)
  { throw new SyntaxErrorException("Tried to use applicate on an quote binding", operator.getSourceInfo());  // FIXME:
  }
}
