package rmk35.partIIProject.middle;

import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTPairMapVisitor;

import rmk35.partIIProject.middle.bindings.SyntaxBinding;

import lombok.Value;
/** Syntax specification is a keyword, transformer specification, see r7rs, 7.1.3, page 64
 */
@Value
public class ASTSyntaxSpecificationVisitor extends ASTPairMapVisitor<String, SyntaxBinding>
{ public ASTSyntaxSpecificationVisitor(Environment environment)
  { super(a -> a.accept(new ASTExpectIdentifierVisitor()).getValue(), b -> b.accept(new ASTTransformerSpecificationVisitor(environment)));
  }
}
