package rmk35.partIIProject.runtime.libraries.base;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ThrowableValue;
import rmk35.partIIProject.runtime.libraries.UnaryLambda;

public class RaiseLambda extends UnaryLambda
{ @Override
  protected Object run(RuntimeValue first)
  { throw new ThrowableValue(first);
  }
}
