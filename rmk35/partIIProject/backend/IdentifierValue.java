package rmk35.partIIProject.backend;

public class IdentifierValue implements RuntimeValue
{ String name;

  public IdentifierValue(String name)
  { this.name = name;
  }

  public String getName()
  { return name;
  }

  @Override
  public boolean eq(RuntimeValue other)
  { return other instanceof IdentifierValue &&
           name.equals(((IdentifierValue)other).getName());
  }

  @Override
  public boolean eqv(RuntimeValue other) { return eq(other); }
  @Override
  public boolean equal(RuntimeValue other) { return eq(other); }
}
