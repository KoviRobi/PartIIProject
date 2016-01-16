package rmk35.partIIProject.frontend.AST;

public abstract class SchemeNumber extends SchemeEquality implements SchemeObject
{ public boolean equal(Object other)
  { return eqv(other);
  }
}
