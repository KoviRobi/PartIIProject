package schemeCall;

import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.RuntimeValue;

public class testf2 extends LambdaValue
{ public RuntimeValue run(RuntimeValue value)
  { System.out.println(value);
    return null;
  }
  public static void main(String... arguments)
  { new testf2().run(null);
  }
}