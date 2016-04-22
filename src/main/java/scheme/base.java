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
  public static Binding lambda = new LambdaSyntaxBinding();
  public static Binding i$000066 = new IfBinding();
  public static Binding define = new DefineBinding();
  public static Binding set$000021 = new SetBinding();
  public static Binding let = new LetBinding();
  public static Binding define_syntax = new DefineSyntaxBinding();
  public static Binding begin = new BeginBinding();
  public static Binding let_syntax = new LetSyntaxBinding();
  public static Binding syntax_rules = new SyntaxRulesBinding();
  public static Binding syntax_error = new SyntaxErrorBinding();
  public static Binding quote = new QuoteBinding();

  public static RuntimeValue raise =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first) { throw new ThrowableValue(first); }
  };

  public static RuntimeValue with_exception_handler =
  new BinaryLambda()
  { @Override
    public RuntimeValue run2(RuntimeValue first, RuntimeValue second)
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
  public static RuntimeValue pair$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue value) { return new BooleanValue(value instanceof ConsValue); }
  };

  public static RuntimeValue cons =
  new BinaryLambda()
  { @Override
    public RuntimeValue run2(RuntimeValue car, RuntimeValue cdr) { return new ConsValue(car, cdr); }
  };

  public static RuntimeValue car =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((ConsValue) cell).getCar(); }
  };

  public static RuntimeValue cdr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((ConsValue) cell).getCdr(); }
  };

  public static RuntimeValue set_car$000021 =
  new BinaryLambda()
  { @Override
    public RuntimeValue run2(RuntimeValue cell, RuntimeValue value) { ((ConsValue) cell).setCar(value); return new UnspecifiedValue(); }
  };

  public static RuntimeValue set_cdr$000021 =
  new BinaryLambda()
  { @Override
    public RuntimeValue run2(RuntimeValue cell, RuntimeValue value) { ((ConsValue) cell).setCdr(value); return new UnspecifiedValue(); }
  };

  public static RuntimeValue caar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) car).run(((UnaryLambda) car).run(cell)); } // FIXME: Tail calls
  };

  public static RuntimeValue cadr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) car).run(((UnaryLambda) cdr).run(cell)); }
  };

  public static RuntimeValue cdar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) cdr).run(((UnaryLambda) car).run(cell)); }
  };

  public static RuntimeValue cddr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) cdr).run(((UnaryLambda) cdr).run(cell)); }
  };

  public static RuntimeValue null$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue value) { return new BooleanValue(value instanceof NullValue); }
  };

  public static RuntimeValue list$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue value)
    { while (value instanceof ConsValue)
      { value = ((ConsValue) value).getCdr();
      }
      return new BooleanValue(value instanceof NullValue);
    }
  };

  public static RuntimeValue make_list =
  new LambdaValue()
  { @Override
    public RuntimeValue run(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public static RuntimeValue list =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue value) { return value; }
  };

  public static RuntimeValue length =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public static RuntimeValue append =
  new LambdaValue()
  { @Override
    public RuntimeValue run(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public static RuntimeValue reverse =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public static RuntimeValue list_tail =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public static RuntimeValue list_ref =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public static RuntimeValue list_set$000021 =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public static RuntimeValue memq =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public static RuntimeValue memv =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public static RuntimeValue member =
  new LambdaValue()
  { @Override
    public RuntimeValue run(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public static RuntimeValue assq =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public static RuntimeValue assv =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public static RuntimeValue assoc =
  new LambdaValue()
  { @Override
    public RuntimeValue run(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  public static RuntimeValue list_copy =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue value) { throw new UnsupportedOperationException("Not yet implemented"); }
  };

  // R7RS, Pairs and lists, section 6.8
  public static RuntimeValue vector_ref =
  new BinaryLambda()
  { @Override
    public RuntimeValue run2(RuntimeValue vector, RuntimeValue k)
    { Object[] javaVector = ((VectorValue) vector).toJavaValue();
      return ValueHelper.toSchemeValue(javaVector[((NumberValue) k).toJavaValue()]);
    }
  };
}
