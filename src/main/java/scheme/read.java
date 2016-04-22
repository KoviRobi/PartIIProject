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
import rmk35.partIIProject.runtime.ValueHelper;
import rmk35.partIIProject.runtime.libraries.BinaryLambda;
import rmk35.partIIProject.runtime.libraries.UnaryLambda;
import rmk35.partIIProject.runtime.libraries.ReflectiveEnvironment;

import rmk35.partIIProject.frontend.SchemeParser;

public class read extends ReflectiveEnvironment
{ public read() { bind(); }
  public RuntimeValue read =
  new LambdaValue()
  { @Override
    public RuntimeValue run(RuntimeValue optionalPort)
    { if (optionalPort instanceof NullValue)
      { RuntimeValue returnValue;
         try
        { returnValue = SchemeParser.read(System.in);
        } catch (InternalCompilerException e)
        { throw new ThrowableValue(new ReadErrorValue());
        }
        return (returnValue == null)
          ? new EndOfFileValue()
          : returnValue;
      } else if (optionalPort instanceof ConsValue &&
          ((ConsValue) optionalPort).getCdr() instanceof NullValue)
      { throw new UnsupportedOperationException("FIXME: ports are not supported yet");
      } else
      { throw new ClassCastException("Too many arguments to \"read\".");
      }
    }
  };
}
