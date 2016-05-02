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

public class numbers extends ReflectiveEnvironment
{ public numbers()
  { bind();
  }

  // R7RS, Numbers, section 6.2
  public static RuntimeValue number$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first) { return ValueHelper.toSchemeValue(first instanceof NumberValue); }
  };

  public static RuntimeValue complex$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first) { return ValueHelper.toSchemeValue(first instanceof ComplexValue); }
  };

  public static RuntimeValue real$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first) { return ValueHelper.toSchemeValue(first instanceof RealValue); }
  };

  public static RuntimeValue rational$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first) { return ValueHelper.toSchemeValue(first instanceof RationalValue); }
  };

  public static RuntimeValue integer$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first) { return ValueHelper.toSchemeValue(first instanceof IntegerValue); }
  };

  public static RuntimeValue exact$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first) { return ValueHelper.toSchemeValue(((NumberValue) first).isExact()); }
  };

  public static RuntimeValue inexact$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first) { return ValueHelper.toSchemeValue(! ((NumberValue) first).isExact()); }
  };

  public static RuntimeValue exact$00002Dinteger$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first) { return ValueHelper.toSchemeValue(first instanceof IntegerValue && ((NumberValue) first).isExact()); }
  };

  public static RuntimeValue $00003D /* = */ =
  new BinaryConjunctOperator()
  { @Override
    public Boolean run2(RuntimeValue first, RuntimeValue second)
    { return ((NumberValue) first).numberEquals((NumberValue) second);
    }
  };

  public static RuntimeValue $00003C /* < */ =
  new BinaryConjunctOperator()
  { @Override
    public Boolean run2(RuntimeValue first, RuntimeValue second)
    { return ((NumberValue) first).numberLessThan((NumberValue) second);
    }
  };

  public static RuntimeValue $00003E /* > */ =
  new BinaryConjunctOperator()
  { @Override
    public Boolean run2(RuntimeValue first, RuntimeValue second)
    { return ((NumberValue) first).numberGreaterThan((NumberValue) second);
    }
  };

  public static RuntimeValue $00003C$00003D /* <= */ =
  new BinaryConjunctOperator()
  { @Override
    public Boolean run2(RuntimeValue first, RuntimeValue second)
    { return ((NumberValue) first).numberLessThanOrEquals((NumberValue) second);
    }
  };

  public static RuntimeValue $00003E$00003D /* >= */ =
  new BinaryConjunctOperator()
  { @Override
    public Boolean run2(RuntimeValue first, RuntimeValue second)
    { return ((NumberValue) first).numberGreaterThanOrEquals((NumberValue) second);
    }
  };

  public static RuntimeValue zero$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first)
    { return ValueHelper.toSchemeValue(((NumberValue) first).isZero());
    }
  };

  public static RuntimeValue positive$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first)
    { return ValueHelper.toSchemeValue(((NumberValue) first).isPositive());
    }
  };

  public static RuntimeValue negative$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first)
    { return ValueHelper.toSchemeValue(! ((NumberValue) first).isNegative());
    }
  };

  public static RuntimeValue odd$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first)
    { return ValueHelper.toSchemeValue(((NumberValue) first).isOdd());
    }
  };

  public static RuntimeValue even$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first)
    { return ValueHelper.toSchemeValue(((NumberValue) first).isEven());
    }
  };

  public static RuntimeValue max$00003F =
  new BinaryLambda()
  { @Override
    public RuntimeValue run2(RuntimeValue a, RuntimeValue b)
    { NumberValue A = (NumberValue) a;
      NumberValue B = (NumberValue) b;
      if (A.isExact() && B.isExact())
      { return A.numberLessThanOrEquals(B) ? A : B;
      } else
      { return A.toInexact().numberLessThanOrEquals(B.toInexact()) ? A.toInexact() : B.toInexact();
      }
    }
  };

  public static RuntimeValue $00002B /* + */ =
  new BinaryOperator<NumberValue>()
  { @Override public NumberValue run2(RuntimeValue first, RuntimeValue second) { return combine((NumberValue) first, (NumberValue) second); }
    @Override public NumberValue initial() { return new IntegerValue(0); }
    @Override public NumberValue combine(NumberValue a, NumberValue b)
    { return a.add(b);
    }
  };

  public static RuntimeValue $00002A /* * */ =
  new BinaryOperator<NumberValue>()
  { @Override public NumberValue run2(RuntimeValue first, RuntimeValue second) { return combine((NumberValue) first, (NumberValue) second); }
    @Override public NumberValue initial() { return new IntegerValue(1); }
    @Override public NumberValue combine(NumberValue a, NumberValue b)
    { return a.multiply(b);
    }
  };

  public static RuntimeValue $00002D /* - */ =
  new VariadicLambda()
  { @Override
    public RuntimeValue run(RuntimeValue arguments)
    { ConsValue first = (ConsValue) arguments;
      if (first.getCdr() instanceof NullValue)
      { return new IntegerValue(0).subtract((NumberValue) first.getCar());
      } else
      { NumberValue returnValue = (NumberValue) first.getCar();
        RuntimeValue next = first.getCdr();
        while (! (next instanceof NullValue))
        { returnValue = returnValue.subtract((NumberValue) ((ConsValue) next).getCar());
          next = ((ConsValue) next).getCdr();
        }
        return returnValue;
      }
    }
  };

  public static RuntimeValue $00002F /* / */ =
  new VariadicLambda()
  { @Override
    public RuntimeValue run(RuntimeValue arguments)
    { ConsValue first = (ConsValue) arguments;
      if (first.getCdr() instanceof NullValue)
      { return new IntegerValue(1).divide((NumberValue) first.getCar());
      } else
      { NumberValue returnValue = (NumberValue) first.getCar();
        RuntimeValue next = first.getCdr();
        while (! (next instanceof NullValue))
        { returnValue = returnValue.divide((NumberValue) ((ConsValue) next).getCar());
          next = ((ConsValue) next).getCdr();
        }
        return returnValue;
      }
    }
  };

//  public static RuntimeValue abs =
//  public static RuntimeValue floor$00002F =
//  public static RuntimeValue floor_quotiend =
//  public static RuntimeValue floor_remainder =
//  public static RuntimeValue truncate$00002F =
//  public static RuntimeValue truncate_quotiend =
//  public static RuntimeValue truncate_remainder =
//  public static RuntimeValue quotient =
//  public static RuntimeValue remainder =
//  public static RuntimeValue modulo =
//  public static RuntimeValue gcd =
//  public static RuntimeValue lcm =
//  public static RuntimeValue numerator =
//  public static RuntimeValue denominator =
//  public static RuntimeValue floor =
//  public static RuntimeValue ceiling =
//  public static RuntimeValue truncate =
//  public static RuntimeValue round =
//  public static RuntimeValue rationalize =
//  public static RuntimeValue square =
//  public static RuntimeValue sqrt =
//  public static RuntimeValue exact-integer-sqrt =
//  public static RuntimeValue expt =
  public static RuntimeValue inexact =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first) { return ValueHelper.toSchemeValue(((NumberValue) first).toInexact()); }
  };

  public static RuntimeValue exact =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first) { return ValueHelper.toSchemeValue(((NumberValue) first).toExact()); }
  };

  public static RuntimeValue number_$00003Estring =
  new UnaryOrBinaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first) { return ValueHelper.toSchemeValue(((NumberValue) first).toString()); }
    public RuntimeValue run2(RuntimeValue first, RuntimeValue second)
    { if (second.eqv(ValueHelper.toSchemeValue(2))) return ValueHelper.toSchemeValue(((NumberValue) first).toString(2));
      if (second.eqv(ValueHelper.toSchemeValue(8))) return ValueHelper.toSchemeValue(((NumberValue) first).toString(8));
      if (second.eqv(ValueHelper.toSchemeValue(10))) return ValueHelper.toSchemeValue(((NumberValue) first).toString(10));
      if (second.eqv(ValueHelper.toSchemeValue(16))) return ValueHelper.toSchemeValue(((NumberValue) first).toString(16));
      throw new NumberFormatException("Incorrect radix of " + second.toString());
    }
  };

  public static RuntimeValue string_$00003Enumber =
  new UnaryOrBinaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue first) { return NumberValue.parse(((StringValue) first).toJavaValue()); }
    public RuntimeValue run2(RuntimeValue first, RuntimeValue second)
    { if (second.eqv(ValueHelper.toSchemeValue(2))) return NumberValue.parse(((StringValue) first).toJavaValue(), 2);
      if (second.eqv(ValueHelper.toSchemeValue(8))) return NumberValue.parse(((StringValue) first).toJavaValue(), 8);
      if (second.eqv(ValueHelper.toSchemeValue(10))) return NumberValue.parse(((StringValue) first).toJavaValue(), 10);
      if (second.eqv(ValueHelper.toSchemeValue(16))) return NumberValue.parse(((StringValue) first).toJavaValue(), 16);
      throw new NumberFormatException("Incorrect radix of " + second.toString());
    }
  };

//  public static RuntimeValue numerator =
//  public static RuntimeValue denominator =
//  public static RuntimeValue floor =
//  public static RuntimeValue ceiling =
//  public static RuntimeValue truncate =
//  public static RuntimeValue round =
//  public static RuntimeValue rationalize =
//  public static RuntimeValue square =
//  public static RuntimeValue sqrt =
//  public static RuntimeValue exact_integer_sqrt =
//  public static RuntimeValue expt =

}
