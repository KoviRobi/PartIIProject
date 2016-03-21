package rmk35.partIIProject.runtime.libraries.base.list;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.libraries.UnaryLambda;

public class Cadr extends UnaryLambda
{ @Override
  protected RuntimeValue run(RuntimeValue first)
  { return ((ConsValue) ((ConsValue) first).getCdr()).getCar();
  }
}
