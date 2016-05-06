package scheme;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.BooleanValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.StringValue;
import rmk35.partIIProject.runtime.numbers.NumberValue;
import rmk35.partIIProject.runtime.numbers.ComplexValue;
import rmk35.partIIProject.runtime.numbers.RealValue;
import rmk35.partIIProject.runtime.numbers.RationalValue;
import rmk35.partIIProject.runtime.numbers.IntegerValue;
import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ErrorValue;
import rmk35.partIIProject.runtime.ThrowableValue;
import rmk35.partIIProject.runtime.ContinuableThrowableValue;
import rmk35.partIIProject.runtime.Trampoline;
import rmk35.partIIProject.runtime.UnspecifiedValue;
import rmk35.partIIProject.runtime.VectorValue;
import rmk35.partIIProject.runtime.ValueHelper;
import rmk35.partIIProject.runtime.libraries.ReflectiveEnvironment;
import rmk35.partIIProject.runtime.libraries.UnaryLambda;
import rmk35.partIIProject.runtime.libraries.BinaryLambda;
import rmk35.partIIProject.runtime.libraries.UnaryOrBinaryLambda;
import rmk35.partIIProject.runtime.libraries.TernaryLambda;
import rmk35.partIIProject.runtime.libraries.VariadicLambda;
import rmk35.partIIProject.runtime.libraries.BinaryOperator;
import rmk35.partIIProject.runtime.libraries.BinaryConjunctOperator;

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

// This is stuff that macros in (scheme base) depend on
public class simple_base extends ReflectiveEnvironment
{ public simple_base()
  { bind();
  }

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

  public static RuntimeValue eq$00003F =
  new BinaryLambda()
  { @Override
    public RuntimeValue run2(RuntimeValue a, RuntimeValue b) { return ValueHelper.toSchemeValue(a.eq(b)); }
  };

  public static RuntimeValue eqv$00003F =
  new BinaryLambda()
  { @Override
    public RuntimeValue run2(RuntimeValue a, RuntimeValue b) { return ValueHelper.toSchemeValue(a.eqv(b)); }
  };

  public static RuntimeValue equal$00003F =
  new BinaryLambda()
  { @Override
    public RuntimeValue run2(RuntimeValue a, RuntimeValue b) { return ValueHelper.toSchemeValue(a.equal(b)); }
  };

  public static RuntimeValue error =
  new VariadicLambda()
  { @Override
    public RuntimeValue run(RuntimeValue arguments) { throw new ThrowableValue(new ErrorValue(arguments)); }
  };
}
