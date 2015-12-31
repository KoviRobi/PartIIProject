package rmk35.partIIProject.backend.runtimeValues;

public class NumberValue implements RuntimeValue
{ Integer value;

  public NumberValue(int value)
  { this(new Integer(value));
  }
  public NumberValue(Integer value)
  { this.value = value;
  }


  public boolean equal(RuntimeValue other)
  { if (other instanceof NumberValue)
    { return value == ((NumberValue)other).value;
    } else
    { return false;
    }
  }

  public boolean eqv(RuntimeValue other)
  { return equal(other);
  }

  public boolean eq(RuntimeValue other)
  { return equal(other);
  }

  public String toString()
  { return value.toString();
  }
}
