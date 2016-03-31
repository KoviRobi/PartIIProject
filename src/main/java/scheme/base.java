package scheme;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.ThrowableValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.BooleanValue;
import rmk35.partIIProject.runtime.TrampolineValue;
import rmk35.partIIProject.runtime.libraries.UnaryLambda;
import rmk35.partIIProject.runtime.libraries.BinaryLambda;

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

public class base extends ReflectiveEnvironment
{ public base() { bind(); }

  // R7RS, Primitive Expressions, section 4.1.
  public Binding lambda = new LambdaSyntaxBinding();
  public Binding iX000066 = new IfBinding();
  public Binding define = new DefineBinding();
  public Binding setX000021 = new SetBinding();
  public Binding let = new LetBinding();
  public Binding defineX00002Dsyntax = new DefineSyntaxBinding();
  public Binding begin = new BeginBinding();
  public Binding letX00002Dsyntax = new LetSyntaxBinding();
  public Binding syntaxX00002Drules = new SyntaxRulesBinding();
  public Binding syntaxX00002Derror = new SyntaxErrorBinding();
  public Binding quote = new QuoteBinding();

  public RuntimeValue raise =
  new UnaryLambda()
  { @Override
    protected RuntimeValue run(RuntimeValue first) { throw new ThrowableValue(first); }
  };

  public RuntimeValue withX00002DexceptionX00002Dhandler =
  new BinaryLambda()
  { @Override
    protected RuntimeValue run(RuntimeValue first, RuntimeValue second)
    { LambdaValue handler = (LambdaValue) first;
      LambdaValue body = (LambdaValue) second;
      try
      { return TrampolineValue.bounceHelper(body.apply(new NullValue()));
      } catch (ThrowableValue exception)
      { return handler.apply(new ConsValue(exception.getValue(), new NullValue()));
      }
    }
  };

  public RuntimeValue car =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue cell) { return ((ConsValue) cell).getCar(); }
  };

  public RuntimeValue cdr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue cell) { return ((ConsValue) cell).getCdr(); }
  };

  public RuntimeValue cons = new BinaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue car, RuntimeValue cdr) { return new ConsValue(car, cdr, null); }
  };

  public RuntimeValue nullX00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value) { return new BooleanValue(value instanceof NullValue, null); }
  };
}
