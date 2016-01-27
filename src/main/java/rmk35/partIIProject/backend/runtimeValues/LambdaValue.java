package rmk35.partIIProject.backend.runtimeValues;

import java.util.List;

public abstract class LambdaValue implements RuntimeValue
{ public boolean equal(RuntimeValue other) { return false; }
  public boolean eqv(RuntimeValue other) { return this == other; }
  public boolean eq(RuntimeValue other) { return this == other; }

  public abstract RuntimeValue run(List<RuntimeValue> arguments);
}
