package rmk35.partIIProject.backend;

import java.util.Map;

public interface RuntimeValue
{ boolean equal(RuntimeValue other);
  boolean eqv(RuntimeValue other);
  boolean eq(RuntimeValue other);
}
