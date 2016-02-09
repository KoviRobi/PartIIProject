package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.runtime.IdentifierValue;

public class ASTExpectIdentifierVisitor extends ASTUnexpectedVisitor<IdentifierValue>
{ @Override
  public IdentifierValue visit(IdentifierValue identifier)
  { return identifier;
  }
}
