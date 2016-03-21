package rmk35.partIIProject.runtime.libraries.base.list;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NumberValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.libraries.UnaryLambda;
import rmk35.partIIProject.runtime.libraries.base.Nullp;

public class Length extends UnaryLambda
{ private static LambdaValue cdr = new Cdr();

  @Override
  protected RuntimeValue run(RuntimeValue first)
  { SourceInfo sourceInfo = first.getSourceInfo();
    int length = 0;
    while (! (first instanceof NullValue))
    { first = cdr.apply(first);
      length++;
    }
    return new NumberValue(length, sourceInfo);
  }
}
