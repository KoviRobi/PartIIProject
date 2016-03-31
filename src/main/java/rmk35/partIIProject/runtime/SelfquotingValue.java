package rmk35.partIIProject.runtime;

public abstract class SelfquotingValue implements PrimitiveValue
{ @Override
  public boolean mutable() { return false; }

  @Override
  public boolean equals(Object other)
  { return other instanceof RuntimeValue && equal((RuntimeValue) other);
  }
}
