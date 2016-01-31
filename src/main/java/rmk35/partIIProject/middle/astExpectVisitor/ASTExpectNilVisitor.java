package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.frontend.AST.SchemeNil;

import rmk35.partIIProject.middle.ASTVisitor;

public class ASTExpectNilVisitor extends ASTUnexpectedVisitor<SchemeNil>
{ @Override
  public SchemeNil visit(SchemeNil nil)
  { return nil;
  }
}
