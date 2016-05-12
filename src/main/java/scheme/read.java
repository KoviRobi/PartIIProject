package scheme;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.ThrowableValue;
import rmk35.partIIProject.runtime.ReadErrorValue;
import rmk35.partIIProject.runtime.EndOfFileValue;
import rmk35.partIIProject.runtime.ports.PortValue;
import rmk35.partIIProject.runtime.ports.InputPortValue;
import rmk35.partIIProject.runtime.ports.OutputPortValue;
import rmk35.partIIProject.runtime.ValueHelper;
import rmk35.partIIProject.runtime.libraries.VariadicLambda;
import rmk35.partIIProject.runtime.libraries.NullaryLambda;
import rmk35.partIIProject.runtime.libraries.NullaryOrUnaryLambda;
import rmk35.partIIProject.runtime.libraries.ReflectiveEnvironment;

import rmk35.partIIProject.frontend.SchemeParser;

public class read extends ReflectiveEnvironment
{ public read() { bind(); }
  public static RuntimeValue read =
  new NullaryOrUnaryLambda()
  { @Override
    public RuntimeValue run0()
    { return run((InputPortValue) ((NullaryLambda) io.current_input_port).run0());
    }

    @Override
    public RuntimeValue run1(RuntimeValue port)
    { return run((InputPortValue) port);
    }

    public RuntimeValue run(InputPortValue  port)
    { RuntimeValue returnValue;
      try
      { returnValue = SchemeParser.read(System.in);
      } catch (InternalCompilerException e)
      { throw new ThrowableValue(new ReadErrorValue());
      }
      return (returnValue == null)
        ? new EndOfFileValue()
        : returnValue;
    }
  };
}
