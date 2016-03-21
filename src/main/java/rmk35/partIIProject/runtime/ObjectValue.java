package rmk35.partIIProject.runtime;

public class ObjectValue extends LambdaValue
{ private Object innerObject;

  public ObjectValue(Object innerObject)
  { this.innerObject = innerObject;
  }

  public Object getValue()
  { return innerObject;
  }

  @Override
  public Object toJavaValue()
  { return innerObject;
  }

  public boolean equal(RuntimeValue other)
  { return this.equals(other); // Java Object equals
  }
  public boolean eqv(RuntimeValue other) { return this == other; }
  public boolean eq(RuntimeValue other) { return this == other; }

  public RuntimeValue apply(RuntimeValue argument)
  { throw new UnsupportedOperationException("FIXME:");
  }
}
