package rmk35.partIIProject.runtime;

import java.util.List;

public abstract class BinaryLambda extends LambdaValue
{ @Override
  public Object run(List<RuntimeValue> arguments)
  { if (arguments.size() != 2)
    { throw new IllegalArgumentException("Expecting two RuntimeValue arguments, got " + arguments);
    } else
    { return run(arguments.get(0), arguments.get(1));
    }
  }

  abstract Object run(RuntimeValue first, RuntimeValue second);
}
