package scheme;

import rmk35.partIIProject.runtime.BooleanValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.NumberValue;
import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ThrowableValue;
import rmk35.partIIProject.runtime.TrampolineValue;
import rmk35.partIIProject.runtime.UnspecifiedValue;
import rmk35.partIIProject.runtime.VectorValue;
import rmk35.partIIProject.runtime.ValueHelper;
import rmk35.partIIProject.runtime.libraries.ReflectiveEnvironment;
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
  public Binding i$000066 = new IfBinding();
  public Binding define = new DefineBinding();
  public Binding set$000021 = new SetBinding();
  public Binding let = new LetBinding();
  public Binding define_syntax = new DefineSyntaxBinding();
  public Binding begin = new BeginBinding();
  public Binding let_syntax = new LetSyntaxBinding();
  public Binding syntax_rules = new SyntaxRulesBinding();
  public Binding syntax_error = new SyntaxErrorBinding();
  public Binding quote = new QuoteBinding();

  public RuntimeValue raise =
  new UnaryLambda()
  { @Override
    protected RuntimeValue run(RuntimeValue first) { throw new ThrowableValue(first); }
  };

  public RuntimeValue with_exception_handler =
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

  // R7RS, Pairs and lists, section 6.4
  public RuntimeValue pair$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value) { return new BooleanValue(value instanceof ConsValue); }
  };

  public RuntimeValue cons = new BinaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue car, RuntimeValue cdr) { return new ConsValue(car, cdr); }
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

  public RuntimeValue set_car$000021 =
  new BinaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue cell, RuntimeValue value) { ((ConsValue) cell).setCar(value); return new UnspecifiedValue(); }
  };

  public RuntimeValue set_cdr$000021 =
  new BinaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue cell, RuntimeValue value) { ((ConsValue) cell).setCdr(value); return new UnspecifiedValue(); }
  };

  public RuntimeValue caar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue cell) { return ((LambdaValue) car).apply(((LambdaValue) car).apply(cell)); } // FIXME: Tail calls
  };

  public RuntimeValue cadr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue cell) { return ((LambdaValue) car).apply(((LambdaValue) cdr).apply(cell)); }
  };

  public RuntimeValue cdar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue cell) { return ((LambdaValue) cdr).apply(((LambdaValue) car).apply(cell)); }
  };

  public RuntimeValue cddr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue cell) { return ((LambdaValue) cdr).apply(((LambdaValue) cdr).apply(cell)); }
  };

  public RuntimeValue null$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value) { return new BooleanValue(value instanceof NullValue); }
  };

  public RuntimeValue list$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value)
    { while (value instanceof ConsValue)
      { value = ((ConsValue) value).getCdr();
      }
      return new BooleanValue(value instanceof NullValue);
    }
  };

  public RuntimeValue make_list =
  new LambdaValue()
  { @Override
    public RuntimeValue apply(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public RuntimeValue list =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value) { return value; }
  };

  public RuntimeValue length =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public RuntimeValue append =
  new LambdaValue()
  { @Override
    public RuntimeValue apply(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public RuntimeValue reverse =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public RuntimeValue list_tail =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public RuntimeValue list_ref =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public RuntimeValue list_set$000021 =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public RuntimeValue memq =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public RuntimeValue memv =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public RuntimeValue member =
  new LambdaValue()
  { @Override
    public RuntimeValue apply(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public RuntimeValue assq =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public RuntimeValue assv =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public RuntimeValue assoc =
  new LambdaValue()
  { @Override
    public RuntimeValue apply(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public RuntimeValue list_copy =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  // R7RS, Pairs and lists, section 6.8
  public RuntimeValue vector_ref =
  new BinaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue vector, RuntimeValue k)
    { Object[] javaVector = ((VectorValue) vector).toJavaValue();
      return ValueHelper.toSchemeValue(javaVector[((NumberValue) k).toJavaValue()]);
    }
  };
}
