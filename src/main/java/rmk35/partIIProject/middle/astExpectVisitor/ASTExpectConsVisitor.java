package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.ASTVisitor;

public class ASTExpectConsVisitor extends ASTUnexpectedVisitor<SchemeCons>
{ @Override
  public SchemeCons visit(SchemeCons consCell)
  { return consCell;
  }
}
