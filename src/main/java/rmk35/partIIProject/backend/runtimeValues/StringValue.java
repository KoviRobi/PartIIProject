package rmk35.partIIProject.backend.runtimeValues;

public class StringValue implements RuntimeValue
{ String value;

  public StringValue(String value)
  { this.value =  value;
  }

  public String toString()
  { return value;
  }

  @Override
  public boolean eq(RuntimeValue other)
  { return this == other;
  }

  @Override
  public boolean eqv(RuntimeValue other)
  { return other instanceof StringValue
        && value.equals(((StringValue)other).value);
  }

  @Override
  public boolean equal(RuntimeValue other) { return eq(other); }
}
