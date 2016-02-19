package rmk35.partIIProject.runtime;

import java.util.List;

public abstract class UnaryLambda extends LambdaValue
{ @Override
  public Object run(List<RuntimeValue> arguments)
  { if (arguments.size() != 1)
    { throw new IllegalArgumentException("Expecting one RuntimeValue arguments, got " + arguments);
    } else
    { return run(arguments.get(0));
    }
  }

  abstract Object run(RuntimeValue first);
}
