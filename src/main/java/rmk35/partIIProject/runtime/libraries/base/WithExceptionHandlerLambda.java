package rmk35.partIIProject.runtime.libraries.base;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.TrampolineValue;
import rmk35.partIIProject.runtime.ThrowableValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.libraries.BinaryLambda;

import java.util.List;
import java.util.ArrayList;

public class WithExceptionHandlerLambda extends BinaryLambda
{ @Override
  protected RuntimeValue run(RuntimeValue first, RuntimeValue second)
  { LambdaValue handler = (LambdaValue) first;
    LambdaValue body = (LambdaValue) second;
    try
    { return TrampolineValue.bounceHelper(body.apply(new NullValue()));
    } catch (ThrowableValue exception)
    { return handler.apply(new ConsValue(exception.getValue(), new NullValue()));
    }
  }
}
