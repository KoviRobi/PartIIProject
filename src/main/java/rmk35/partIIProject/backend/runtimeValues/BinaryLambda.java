package rmk35.partIIProject.backend.runtimeValues;

import java.util.List;

public abstract class BinaryLambda extends LambdaValue
{ public RuntimeValue run(List<RuntimeValue> arguments)
  { if (arguments.size() != 2)
    { throw new IllegalArgumentException("Expecting two RuntimeValue arguments, got " + arguments);
    } else
    { return run(arguments.get(0), arguments.get(1));
    }
  }

  abstract RuntimeValue run(RuntimeValue first, RuntimeValue second);
}
