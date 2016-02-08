package rmk35.partIIProject.runtime;

public class BooleanValue implements RuntimeValue
{ boolean value;

  public BooleanValue(boolean value)
  { this.value =  value;
  }

  public boolean getValue()
  { return value;
  }

  @Override
  public boolean eq(RuntimeValue other)
  { return other instanceof BooleanValue
        && getValue() == ((BooleanValue)other).getValue();
  }

  @Override
  public boolean eqv(RuntimeValue other) { return eq(other); }
  @Override
  public boolean equal(RuntimeValue other) { return eq(other); }
}
