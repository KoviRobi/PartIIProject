package rmk35.partIIProject.runtime.libraries;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NumberValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.ConsValue;

import java.util.List;

public abstract class VariadicLambda extends LambdaValue
{ @Override
  public final RuntimeValue apply(RuntimeValue arguments)
  { return run(arguments);
  }

  public RuntimeValue run(RuntimeValue argument, RuntimeValue continuation, int programme_counter) { throw new InternalCompilerException("Called run for a built in function"); }

  public abstract RuntimeValue run(RuntimeValue arguments);
}
