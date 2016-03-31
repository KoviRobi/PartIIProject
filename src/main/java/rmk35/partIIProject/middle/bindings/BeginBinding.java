package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.utility.Pair;
import rmk35.partIIProject.utility.PerfectBinaryTree;
import rmk35.partIIProject.utility.PerfectBinaryTreeLeaf;
import rmk35.partIIProject.utility.PerfectBinaryTreeNode;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;
import rmk35.partIIProject.middle.ASTMatcher;
import rmk35.partIIProject.middle.ASTConvertVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.BeginStatement;
import rmk35.partIIProject.backend.statements.DefineStatement;

import java.util.List;
import java.util.ArrayList;

import lombok.ToString;

@ToString
public class BeginBinding extends SintacticBinding
{ @Override
  public Statement applicate(EnvironmentValue environment, RuntimeValue operator, RuntimeValue operands)
  { ASTMatcher beginSubstitution = new ASTMatcher("(body ...)", operands);
    if (beginSubstitution.matched())
    { /* Case for simple let */
      List<Statement> beginStatements = new ArrayList<>();
      if (beginSubstitution.get("body") != null)
      { beginSubstitution.get("body").forEach(value -> beginStatements.add(value.accept(new ASTConvertVisitor(environment))));
      }
      return new BeginStatement(beginStatements);
    }
    throw new SyntaxErrorException("Malformed begin", operator.getSourceInfo());
  }
}
