package rmk35.partIIProject.backend.runtimeValues;

import java.util.List;

public abstract class UnaryLambda extends LambdaValue
{ public RuntimeValue run(List<RuntimeValue> arguments)
  { if (arguments.size() != 1)
    { throw new IllegalArgumentException("Expecting one RuntimeValue arguments, got " + arguments);
    } else
    { return run(arguments.get(0));
    }
  }

  abstract RuntimeValue run(RuntimeValue first);
}
