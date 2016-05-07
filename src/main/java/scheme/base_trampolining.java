package scheme;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.BooleanValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ThrowableValue;
import rmk35.partIIProject.runtime.ContinuableThrowableValue;
import rmk35.partIIProject.runtime.Trampoline;
import rmk35.partIIProject.runtime.CallValue;
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

public class base_trampolining extends base
{ public static RuntimeValue raise =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first) { throw new ThrowableValue(first); }
  };

  public static RuntimeValue with_exception_handler =
  new BinaryLambda()
  { public RuntimeValue run2(RuntimeValue first, RuntimeValue second)
    { LambdaValue handler = (LambdaValue) first;
      LambdaValue body = (LambdaValue) second;
      try
      { return Trampoline.bounce(body.apply(new NullValue()));
      } catch (ThrowableValue exception)
      { return new CallValue(handler, new ConsValue(exception.getValue(), new NullValue()));
      }
    }
  };

  public static RuntimeValue dynamic_wind =
  new TernaryLambda()
  { @Override
    public RuntimeValue run3(RuntimeValue before, RuntimeValue body, RuntimeValue after)
    { try
      { Trampoline.bounce(new CallValue((LambdaValue) before, new NullValue()));
        return Trampoline.bounce(new CallValue((LambdaValue) body, new NullValue()));
      } finally
      { Trampoline.bounce(new CallValue((LambdaValue) after, new NullValue()));
      }
    }
  };

  public static RuntimeValue apply =
  new VariadicLambda()
  { @Override
    public RuntimeValue run(RuntimeValue arguments) // (apply proc arg1 ... args)
    { ConsValue first = (ConsValue) arguments;
      LambdaValue function = (LambdaValue) first.getCar();
      // append! (arg1 ...) to args
      ConsValue second = (ConsValue) first.getCdr();
      ConsValue end = second;
      while (end.getCdr() instanceof ConsValue) { end = (ConsValue) end.getCdr(); }
      if (! (end.getCdr() instanceof NullValue)) throw new IllegalArgumentException("append expects a proper list!");
      end.setCar(((ConsValue) end.getCar()).getCar());
      return new CallValue(function, second);
    }
  };
}
