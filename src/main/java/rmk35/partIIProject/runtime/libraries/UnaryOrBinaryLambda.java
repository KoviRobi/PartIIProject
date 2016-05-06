package rmk35.partIIProject.runtime.libraries;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.ConsValue;

import java.util.List;

public abstract class UnaryOrBinaryLambda extends LambdaValue
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
    } else if (length == 2)
    { return run2(((ConsValue) arguments).getCar(), ((ConsValue) ((ConsValue) arguments).getCdr()).getCar());
    } else
    { throw new IllegalArgumentException(this.getClass().getName() + " is expecting one or two RuntimeValue arguments, got " + arguments);
    }
  }

  public RuntimeValue run(RuntimeValue argument) { throw new InternalCompilerException("Called run for a built in function"); }

  public abstract RuntimeValue run1(RuntimeValue first);
  public abstract RuntimeValue run2(RuntimeValue first, RuntimeValue second);
}
