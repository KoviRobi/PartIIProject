package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.runtime.NullValue;

public class ASTExpectNilVisitor extends ASTUnexpectedVisitor<NullValue>
{ @Override
  public NullValue visit(NullValue nil)
  { return nil;
  }
}
