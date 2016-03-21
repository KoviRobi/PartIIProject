package rmk35.partIIProject.runtime.libraries.base;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.BooleanValue;
import rmk35.partIIProject.runtime.libraries.UnaryLambda;

public class Nullp extends UnaryLambda
{ @Override
  protected RuntimeValue run(RuntimeValue first)
  { return new BooleanValue(first instanceof NullValue);
  }
}
