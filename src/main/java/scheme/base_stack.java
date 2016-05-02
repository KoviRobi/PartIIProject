package scheme;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.BooleanValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ThrowableValue;
import rmk35.partIIProject.runtime.ContinuableThrowableValue;
import rmk35.partIIProject.runtime.CallValue;
import rmk35.partIIProject.runtime.CallStack;
import rmk35.partIIProject.runtime.ContinuationValue;
import rmk35.partIIProject.runtime.UnspecifiedValue;
import rmk35.partIIProject.runtime.VectorValue;
import rmk35.partIIProject.runtime.ValueHelper;
import rmk35.partIIProject.runtime.libraries.ReflectiveEnvironment;
import rmk35.partIIProject.runtime.libraries.UnaryLambda;
import rmk35.partIIProject.runtime.libraries.BinaryLambda;
import rmk35.partIIProject.runtime.libraries.TernaryLambda;
import rmk35.partIIProject.runtime.libraries.VariadicLambda;

import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.bindings.LambdaSyntaxBinding;
import rmk35.partIIProject.middle.bindings.IfBinding;
import rmk35.partIIProject.middle.bindings.BeginBinding;
import rmk35.partIIProject.middle.bindings.DefineBinding;
import rmk35.partIIProject.middle.bindings.SetBinding;
import rmk35.partIIProject.middle.bindings.LetBinding;
import rmk35.partIIProject.middle.bindings.QuoteBinding;
import rmk35.partIIProject.middle.bindings.DefineSyntaxBinding;
import rmk35.partIIProject.middle.bindings.LetSyntaxBinding;
import rmk35.partIIProject.middle.bindings.SyntaxRulesBinding;
import rmk35.partIIProject.middle.bindings.SyntaxErrorBinding;

public class base_stack extends base
{ public static RuntimeValue raise =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first)
    { if (CallStack.restoreHandler())
      { return new ThrowableValue(first);
      } else
      { throw new ThrowableValue(first);
      }
    }
  };

  public static RuntimeValue raise_continuable =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first)
    { return new ContinuableThrowableValue(first, CallStack.getContinuation());
    }
  };

  public static RuntimeValue with_exception_handler =
  new BinaryLambda()
  { public RuntimeValue run2(RuntimeValue first, RuntimeValue second)
    { CallStack.addHandler((LambdaValue) second);
      return new CallValue((LambdaValue) first, new NullValue());
    }
  };

  public static RuntimeValue dynamic_wind =
  new TernaryLambda()
  { @Override
    public RuntimeValue run3(RuntimeValue before, RuntimeValue body, RuntimeValue after)
    { // NEXT: Push before onto stack (which should add the dynamic point)
      // NEXT: Push current value onto stack
      // NEXT: Push after onto stack (which should remove the dynamic point)
      // CallStack.addDynamicPoint((LambdaValue) before, (LambdaValue) after);
      return null;
    }
  };

  public static RuntimeValue call_with_current_continuation =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue function)
    { return new CallValue((LambdaValue) function, new ConsValue(CallStack.getContinuation(), new NullValue()));
    }
  };
  public static RuntimeValue call$00002Fcc =  call_with_current_continuation;
}
