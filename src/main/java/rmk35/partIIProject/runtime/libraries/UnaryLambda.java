package rmk35.partIIProject.runtime.libraries;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;

import java.util.List;

public abstract class UnaryLambda extends LambdaValue
{ @Override
  public final Object run(List<RuntimeValue> arguments)
  { if (arguments.size() != 1)
    { throw new IllegalArgumentException("Expecting one RuntimeValue arguments, got " + arguments);
    } else
    { return run(arguments.get(0));
    }
  }

  protected abstract Object run(RuntimeValue first);
}
