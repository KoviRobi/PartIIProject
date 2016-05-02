package rmk35.partIIProject.runtime.libraries;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.ConsValue;

import java.util.List;

public abstract class TernaryLambda extends LambdaValue
{ @Override
  public final RuntimeValue apply(RuntimeValue arguments)
  { int length = 0;
    RuntimeValue lengthList = arguments;
    while (! (lengthList instanceof NullValue))
    { lengthList = ((ConsValue) lengthList).getCdr();
      length++;
    }
    if (length == 3)
    { return run3(((ConsValue) arguments).getCar(),
    ((ConsValue) ((ConsValue) arguments).getCdr()).getCar(),
    ((ConsValue) ((ConsValue) ((ConsValue) arguments).getCdr()).getCdr()).getCar());
    } else
    { throw new IllegalArgumentException("Expecting two RuntimeValue arguments, got " + arguments);
    }
  }

  public RuntimeValue run(RuntimeValue argument) { throw new InternalCompilerException("Called run for a built in function"); }

  public abstract RuntimeValue run3(RuntimeValue first, RuntimeValue second, RuntimeValue third);
}
