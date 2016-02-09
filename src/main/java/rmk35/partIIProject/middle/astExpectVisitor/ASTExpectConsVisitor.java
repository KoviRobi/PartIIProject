package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.runtime.ConsValue;

public class ASTExpectConsVisitor extends ASTUnexpectedVisitor<ConsValue>
{ @Override
  public ConsValue visit(ConsValue consCell)
  { return consCell;
  }
}
