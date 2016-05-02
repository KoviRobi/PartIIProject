package rmk35.partIIProject.runtime.libraries;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.ValueHelper;

import java.util.List;

public abstract class BinaryOperator<T> extends LambdaValue
{ @Override
  public final RuntimeValue apply(RuntimeValue arguments)
  { T returnValue = initial();
    RuntimeValue first = arguments;
    RuntimeValue second = ((ConsValue) first).getCdr();
    while (! (second instanceof NullValue))
    { RuntimeValue car = ((ConsValue) first).getCar();
      RuntimeValue cadr = ((ConsValue) second).getCar();
      returnValue = combine(returnValue, run2(car, cadr));
      first = second;
      second = ((ConsValue) second).getCdr();
    }
    return ValueHelper.toSchemeValue(returnValue);
  }

  public RuntimeValue run(RuntimeValue argument) { throw new InternalCompilerException("Called run for a built in function"); }

  public abstract T run2(RuntimeValue first, RuntimeValue second);
  public abstract T combine(T a, T b);
  public abstract T initial();
}
