package rmk35.partIIProject.runtime.libraries;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NumberValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.ConsValue;

import java.util.List;

public abstract class UnaryLambda extends LambdaValue
{ @Override
  public final RuntimeValue apply(RuntimeValue arguments)
  { int length = 0;
    RuntimeValue lengthList = arguments;
    while (! (lengthList instanceof NullValue))
    { lengthList = ((ConsValue) lengthList).getCdr();
      length++;
    }
    if (length == 1)
    { return run1(((ConsValue) arguments).getCar());
    } else
    { throw new IllegalArgumentException("Expecting one RuntimeValue arguments, got " + arguments);
    }
  }

  public RuntimeValue run(RuntimeValue argument, RuntimeValue continuation, int programme_counter) { throw new InternalCompilerException("Called run for a built in function"); }

  public abstract RuntimeValue run1(RuntimeValue first);
}
