package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.frontend.AST.SchemeIdentifier;

public class ASTExpectIdentifierVisitor extends ASTUnexpectedVisitor<SchemeIdentifier>
{ @Override
  public SchemeIdentifier visit(SchemeIdentifier identifier)
  { return identifier;
  }
}
