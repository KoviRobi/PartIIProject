package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.Compiler;
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

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.BeginStatement;
import rmk35.partIIProject.backend.statements.DefineStatement;

import java.util.List;
import java.util.ArrayList;

import lombok.ToString;

@ToString
public class LetBinding extends SintacticBinding
{ @Override
  public Statement applicate(EnvironmentValue environment, OutputClass outputClass, MainClass mainClass, RuntimeValue operator, RuntimeValue operands)
  { //  Copy environment for lexical effect
    ASTMatcher simpleLetSubstitution = new ASTMatcher(Compiler.baseEnvironment, environment, "(((name value) ...) body ...)", operands);
    if (simpleLetSubstitution.matched())
    { /* Case for named let */
      return simpleLetSubstitution.convert(outputClass, mainClass,
        "((lambda (name ...) body ...)\n" +
        "value ...)");
    }

    ASTMatcher namedLetSubstitution = new ASTMatcher(Compiler.baseEnvironment, environment, "(loop-name ((name value) ...) body ...)", operands);
    if (namedLetSubstitution.matched())
    { /* Case for named let */
      return namedLetSubstitution.convert(outputClass, mainClass,
        "(begin\n" +
        "  (define loop-name (begin))\n" +
        "  (set! loop-name (lambda (name ...) body ...))\n" +
        "  (loop-name value ...))");
    }
    throw new SyntaxErrorException("Malformed let", operator.getSourceInfo());
  }
}
