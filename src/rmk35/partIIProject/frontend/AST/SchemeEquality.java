package rmk35.partIIProject.frontend.AST;

public abstract class SchemeEquality
{ public boolean eq(Object other)
  { return this == other;
  }

  public abstract boolean eqv(Object other);
  public abstract boolean equal(Object other);
}
