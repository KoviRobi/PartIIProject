package scheme;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.UnspecifiedValue;
import rmk35.partIIProject.runtime.ValueHelper;
import rmk35.partIIProject.runtime.libraries.BinaryLambda;
import rmk35.partIIProject.runtime.libraries.UnaryLambda;
import rmk35.partIIProject.runtime.libraries.ReflectiveEnvironment;

public class write extends ReflectiveEnvironment
{ public write() { bind(); }

  public RuntimeValue write = // FIXME:
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue object)
    { System.out.println(object.writeString());
      return new UnspecifiedValue();
    }
  };
}