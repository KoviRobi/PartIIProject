package rmk35.partIIProject.runtime.libraries.base;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.TrampolineValue;
import rmk35.partIIProject.runtime.ThrowableValue;
import rmk35.partIIProject.runtime.libraries.BinaryLambda;

import java.util.List;
import java.util.ArrayList;

public class WithExceptionHandlerLambda extends BinaryLambda
{ @Override
  protected Object run(RuntimeValue first, RuntimeValue second)
  { LambdaValue handler = (LambdaValue) first;
    LambdaValue body = (LambdaValue) second;
    try
    { return TrampolineValue.bounceHelper(body.run(new ArrayList<RuntimeValue>()));
    } catch (ThrowableValue exception)
    { List<RuntimeValue> argument = new ArrayList<>();
      argument.add(exception.getValue());
      return handler.run(argument);
    }
  }
}
