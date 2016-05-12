package rmk35.partIIProject.runtime;

public abstract class SelfquotingValue implements PrimitiveValue
{ RuntimeValue nextValue = null;

  @Override
  public boolean mutable() { return false; }

  @Override
  public boolean equals(Object other)
  { return other instanceof RuntimeValue && equal((RuntimeValue) other);
  }

  public RuntimeValue getNext()
  { return nextValue;
  }

  public void setNext(RuntimeValue nextValue)
  { this.nextValue = nextValue;
  }
}
