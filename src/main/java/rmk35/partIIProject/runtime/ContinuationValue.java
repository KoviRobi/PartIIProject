package rmk35.partIIProject.runtime;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.utility.Pair;
import rmk35.partIIProject.utility.FunctionalList;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.MalformedParametersException;

import lombok.Value;

@Value
public class ContinuationValue extends LambdaValue
{ FunctionalList<CallFrame> callStack;
  FunctionalList<Pair<LambdaValue, LambdaValue>> dynamicPoints;
  FunctionalList<RuntimeValue> valueStack;
  int programmeCounter;

  @Override
  public Object toJavaValue()
  { return this;
  }

  @Override
  public String writeString()
  { return "#<continuation: " + toString() + ">";
  }

  public boolean equal(RuntimeValue other)
  { return other instanceof ContinuationValue &&
      callStack.equals(((ContinuationValue) other).callStack); // Java Object equals
  }
  public boolean eqv(RuntimeValue other) { return this == other; }
  public boolean eq(RuntimeValue other) { return this == other; }

  public RuntimeValue apply(RuntimeValue argument)
  { CallStack.getCurrentCallStack().setContinuation(this);
    // NEXT: Do values
    return ((ConsValue) argument).getCar();
  }

  public RuntimeValue run(RuntimeValue argument) { throw new InternalCompilerException("Called run for a built in function"); }
}
