package scheme;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.BooleanValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ErrorValue;
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
    { if (CallStack.getCurrentCallStack().restoreHandler())
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
    { return new ContinuableThrowableValue(first, CallStack.getCurrentCallStack().getContinuation());
    }
  };

  public static RuntimeValue error =
  new VariadicLambda()
  { @Override
    public RuntimeValue run(RuntimeValue arguments)
    { return new ThrowableValue(new ErrorValue(arguments));
    }
  };

  public static RuntimeValue with_exception_handler =
  new BinaryLambda()
  { LambdaValue handler;

    public RuntimeValue run2(RuntimeValue handler, RuntimeValue body)
    { CallStack currentCallStack = CallStack.getCurrentCallStack();
      currentCallStack.addFrame(this);
      currentCallStack.addHandler();
      this.handler = (LambdaValue) handler;
      return new CallValue((LambdaValue) body, new NullValue());
    }

    public RuntimeValue run(RuntimeValue argument)
    { CallStack currentCallStack = CallStack.getCurrentCallStack();
      int programmeCounter = currentCallStack.getProgrammeCounter();
      if (programmeCounter != 1) currentCallStack.invalidJump(argument);
      if (argument instanceof ThrowableValue)
      { return new CallValue(handler, new ConsValue(((ThrowableValue) argument).getValue(), new NullValue()));
      } else
      { return argument;
      }
    }
  };

  public static RuntimeValue dynamic_wind =
  new TernaryLambda()
  { LambdaValue before = null;
    LambdaValue body = null;
    LambdaValue after = null;
    RuntimeValue returnValue = null;

    @Override
    public RuntimeValue run3(RuntimeValue before, RuntimeValue body, RuntimeValue after)
    { this.before = (LambdaValue) before;
      this.body = (LambdaValue) body;
      this.after = (LambdaValue) after;
      return run(new NullValue());
    }
    @Override
    public RuntimeValue run(RuntimeValue arguments)
    { CallStack currentCallStack = CallStack.getCurrentCallStack();
      int programmeCounter = currentCallStack.getProgrammeCounter();
       switch (programmeCounter)
       { case 0:
           currentCallStack.addFrame(this);
           return new CallValue(before, new NullValue());
         case 1:
           currentCallStack.addDynamicPoint(before, after);
           currentCallStack.addFrame(this);
           return new CallValue(body, new NullValue());
         case 2:
           returnValue = arguments;
           currentCallStack.removeDynamicPoint();
           currentCallStack.addFrame(this);
           return new CallValue(after, new NullValue());
        case 3:
          return returnValue;
        default:
           currentCallStack.invalidJump(arguments);
       }
       return null; // Never gets here because invalidJump throws an exception
    }
  };

  public static RuntimeValue call_with_current_continuation =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue function)
    { return new CallValue((LambdaValue) function, new ConsValue(CallStack.getCurrentCallStack().getContinuation(), new NullValue()));
    }
  };
  public static RuntimeValue call$00002Fcc =  call_with_current_continuation;

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
      if (! (end.getCdr() instanceof NullValue)) throw new RuntimeException("append expects a proper list!");
      end.setCar(((ConsValue) end.getCar()).getCar());
      return new CallValue(function, second);
    }
  };
}
