package rmk35.partIIProject.middle;

import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTPairMapVisitor;

import rmk35.partIIProject.middle.bindings.SyntaxBinding;

import rmk35.partIIProject.backend.MainClass;

import lombok.Value;
/** Syntax specification is a keyword, transformer specification, see r7rs, 7.1.3, page 64
 */
@Value
public class ASTSyntaxSpecificationVisitor extends ASTPairMapVisitor<String, SyntaxBinding>
{ public ASTSyntaxSpecificationVisitor(EnvironmentValue environment, MainClass mainClass)
  { super(a -> a.accept(new ASTExpectIdentifierVisitor()).getValue(), b -> b.accept(new ASTTransformerSpecificationVisitor(environment, mainClass)));
  }
}
