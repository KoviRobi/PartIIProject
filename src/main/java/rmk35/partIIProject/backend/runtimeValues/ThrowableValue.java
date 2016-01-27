package rmk35.partIIProject.backend.runtimeValues;

public class ThrowableValue extends RuntimeException implements RuntimeValue
{ RuntimeValue value;

  public ThrowableValue(RuntimeValue value)
  { this.value =  value;
  }

  public RuntimeValue getValue()
  { return value;
  }

  @Override
  public boolean eq(RuntimeValue other)
  { return other.eq(value);
  }

  @Override
  public boolean eqv(RuntimeValue other)
  { return other.eqv(value);
  }

  @Override
  public boolean equal(RuntimeValue other)
  { return other.equal(value);
  }

  @Override
  public String getMessage()
  { return "Uncaught raise: " + value.toString();
  }
}
