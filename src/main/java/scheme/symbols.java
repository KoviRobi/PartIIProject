package scheme;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.BooleanValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.StringValue;
import rmk35.partIIProject.runtime.IdentifierValue;
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

public class symbols extends ReflectiveEnvironment
{ public symbols()
  { bind();
  }

  // R7RS, Symbols, section 6.5
  public static RuntimeValue symbol$00003F =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue argument) { return ValueHelper.toSchemeValue(argument instanceof IdentifierValue); }
  };

  public static RuntimeValue symbol$00003D$00003F =
  new BinaryConjunctOperator()
  { @Override
    public Boolean run2(RuntimeValue first, RuntimeValue second)
    { return first instanceof IdentifierValue &&
        second instanceof IdentifierValue &&
        first.eq(second);
    }
  };

  public static RuntimeValue symbol$00002D$00003Estring =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue symbol)
    { return new StringValue(((IdentifierValue) symbol).getValue());
    }
  };

  public static RuntimeValue string$00002D$00003Esymbol =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue string)
    { return new IdentifierValue(((StringValue) string).getValue());
    }
  };
}
