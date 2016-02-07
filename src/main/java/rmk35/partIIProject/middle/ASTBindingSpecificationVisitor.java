package rmk35.partIIProject.middle;

import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTPairMapVisitor;

import rmk35.partIIProject.backend.statements.Statement;

import lombok.Value;
/** Syntax specification is a keyword, transformer specification, see r7rs, 7.1.3, page 64
 */
@Value
public class ASTBindingSpecificationVisitor extends ASTPairMapVisitor<String, Statement>
{ public ASTBindingSpecificationVisitor(Environment environment)
  { super(a -> a.accept(new ASTExpectIdentifierVisitor()).getData(), b -> b.accept(new ASTConvertVisitor(environment)));
  }
}