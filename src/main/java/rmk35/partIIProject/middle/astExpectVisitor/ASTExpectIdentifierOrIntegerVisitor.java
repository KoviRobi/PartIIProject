package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.numbers.IntegerValue;

public class ASTExpectIdentifierOrIntegerVisitor extends ASTUnexpectedVisitor<String>
{ @Override
  public String visit(IdentifierValue identifier)
  { return identifier.getValue();
  }

  @Override
  public String visit(IntegerValue integer)
  { return integer.toString();
  }
}
