package rmk35.partIIProject.runtime.libraries.base.list;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NumberValue;
import rmk35.partIIProject.runtime.libraries.BinaryLambda;
import rmk35.partIIProject.runtime.libraries.base.Nullp;

public class Reference extends BinaryLambda
{ private static final LambdaValue nullp = new Nullp();
  private static final LambdaValue car = new Car();
  private static final LambdaValue cdr = new Cdr();

  @Override
  protected RuntimeValue run(RuntimeValue first, RuntimeValue second)
  { int offset = ((NumberValue) second).getValue();
    while (offset != 0)
    { first = cdr.apply(first);
      offset--;
    }
    return car.apply(first);
  }
}
