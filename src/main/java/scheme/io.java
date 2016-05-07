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
import rmk35.partIIProject.runtime.ports.PortValue;
import rmk35.partIIProject.runtime.ports.InputPortValue;
import rmk35.partIIProject.runtime.ports.OutputPortValue;
import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ThrowableValue;
import rmk35.partIIProject.runtime.ContinuableThrowableValue;
import rmk35.partIIProject.runtime.Trampoline;
import rmk35.partIIProject.runtime.UnspecifiedValue;
import rmk35.partIIProject.runtime.VectorValue;
import rmk35.partIIProject.runtime.ValueHelper;
import rmk35.partIIProject.runtime.libraries.ReflectiveEnvironment;
import rmk35.partIIProject.runtime.libraries.NullaryLambda;
import rmk35.partIIProject.runtime.libraries.UnaryLambda;
import rmk35.partIIProject.runtime.libraries.BinaryLambda;
import rmk35.partIIProject.runtime.libraries.NullaryOrUnaryLambda;
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

public class io extends ReflectiveEnvironment
{ public io()
  { bind();
  }

  static InputPortValue current_input = new InputPortValue(System.in);
  static OutputPortValue current_output = new OutputPortValue(System.out);
  static OutputPortValue current_error = new OutputPortValue(System.err);

  // R7RS, Input and output, section 6.13
  // Ports
  public static RuntimeValue call_with_port = null;
  public static RuntimeValue input_port$00003F = null;
  public static RuntimeValue output_port$00003F = null;
  public static RuntimeValue textual_port$00003F = null;
  public static RuntimeValue binary_port$00003F = null;
  public static RuntimeValue port$00003F = null;
  public static RuntimeValue current_input_port =
  new NullaryLambda()
  { @Override
    public RuntimeValue run0()
    { return current_input;
    }
  };

  public static RuntimeValue current_output_port =
  new NullaryLambda()
  { @Override
    public RuntimeValue run0()
    { return current_output;
    }
  };

  public static RuntimeValue current_error_port =
  new NullaryLambda()
  { @Override
    public RuntimeValue run0()
    { return current_error;
    }
  };

  public static RuntimeValue close_port = null;
  public static RuntimeValue close_input_port = null;
  public static RuntimeValue close_output_port = null;
  public static RuntimeValue open_input_string = null;
  public static RuntimeValue open_output_string = null;
  public static RuntimeValue get_output_string = null;
  public static RuntimeValue open_input_bytevector = null;
  public static RuntimeValue open_output_bytevector = null;
  public static RuntimeValue get_output_bytevector = null;
  // Input
  public static RuntimeValue read_char = null;
  public static RuntimeValue peek_char = null;
  public static RuntimeValue read_line = null;
  public static RuntimeValue eof_object$00003F = null;
  public static RuntimeValue eof_object = null;
  public static RuntimeValue char_ready$00003F = null;
  public static RuntimeValue read_string = null;
  public static RuntimeValue read_u8 = null;
  public static RuntimeValue peek_u8 = null;
  public static RuntimeValue u8_ready = null;
  public static RuntimeValue read_bytevector = null;
  public static RuntimeValue read_bytevector$000021 = null;
  // Output
  public static RuntimeValue newline =
  new NullaryOrUnaryLambda()
  { @Override
    public RuntimeValue run0()
    { ((OutputPortValue) ((NullaryLambda) current_output_port).run0()).println("");
      return new UnspecifiedValue();
    }
    public RuntimeValue run1(RuntimeValue port)
    { ((OutputPortValue) port).println("");
      return new UnspecifiedValue();
    }
  };
  public static RuntimeValue write_char = null;
  public static RuntimeValue write_string = null;
  public static RuntimeValue write_u8 = null;
  public static RuntimeValue write_bytevector = null;
  public static RuntimeValue flush_output_port = null;
}
