package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.frontend.SchemeParserException;

public abstract class SchemeNumber extends SchemeEquality implements SchemeObject
{ public boolean equal(Object other)
  { return eqv(other);
  }
}
