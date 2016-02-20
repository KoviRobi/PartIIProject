package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.ASTQuoteVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectNilVisitor;

import rmk35.partIIProject.backend.statements.Statement;

import lombok.ToString;

@ToString
public class QuoteBinding extends SintacticBinding
{ @Override
  public Statement applicate(Environment useEnvironment, RuntimeValue operator, RuntimeValue operands)
  { ConsValue first = operands.accept(new ASTExpectConsVisitor());
    Statement quoted = first.getCar().accept(new ASTQuoteVisitor());
    first.getCdr().accept(new ASTExpectNilVisitor());
    return quoted;
    }
}
