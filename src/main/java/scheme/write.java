package scheme;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.UnspecifiedValue;
import rmk35.partIIProject.runtime.ValueHelper;
import rmk35.partIIProject.runtime.ports.PortValue;
import rmk35.partIIProject.runtime.ports.InputPortValue;
import rmk35.partIIProject.runtime.ports.OutputPortValue;
import rmk35.partIIProject.runtime.libraries.NullaryLambda;
import rmk35.partIIProject.runtime.libraries.UnaryLambda;
import rmk35.partIIProject.runtime.libraries.BinaryLambda;
import rmk35.partIIProject.runtime.libraries.NullaryOrUnaryLambda;
import rmk35.partIIProject.runtime.libraries.UnaryOrBinaryLambda;
import rmk35.partIIProject.runtime.libraries.ReflectiveEnvironment;

public class write extends ReflectiveEnvironment
{ public write() { bind(); }

  public static RuntimeValue write =
  new UnaryOrBinaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue object)
    { ((OutputPortValue) ((NullaryLambda) io.current_output_port).run0()).print(object.writeString());
      return new UnspecifiedValue();
    }
    public RuntimeValue run2(RuntimeValue object, RuntimeValue port)
    { ((OutputPortValue) port).print(object.writeString());
      return new UnspecifiedValue();
    }
  };

  public static RuntimeValue display =
  new UnaryOrBinaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue object)
    { ((OutputPortValue) ((NullaryLambda) io.current_output_port).run0()).print(object.displayString());
      return new UnspecifiedValue();
    }
    public RuntimeValue run2(RuntimeValue object, RuntimeValue port)
    { ((OutputPortValue) port).print(object.displayString());
      return new UnspecifiedValue();
    }
  };

  public static RuntimeValue writeln =
  new UnaryOrBinaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue object)
    { ((OutputPortValue) ((NullaryLambda) io.current_output_port).run0()).println(object.writeString());
      return new UnspecifiedValue();
    }
    public RuntimeValue run2(RuntimeValue object, RuntimeValue port)
    { ((OutputPortValue) port).println(object.writeString());
      return new UnspecifiedValue();
    }
  };

  public static RuntimeValue displayln =
  new UnaryOrBinaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue object)
    { ((OutputPortValue) ((NullaryLambda) io.current_output_port).run0()).println(object.displayString());
      return new UnspecifiedValue();
    }
    public RuntimeValue run2(RuntimeValue object, RuntimeValue port)
    { ((OutputPortValue) port).println(object.displayString());
      return new UnspecifiedValue();
    }
  };
}
