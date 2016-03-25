package rmk35.partIIProject.runtime;

import java.lang.reflect.Method;

public class MethodValue extends LambdaValue
{ private Method innerMethod;

  public MethodValue(Method innerMethod)
  { this.innerMethod = innerMethod;
  }

  public Method getValue()
  { return innerMethod;
  }

  public String toString()
  { return innerMethod.toString();
  }

  @Override
  public Method toJavaValue()
  { return innerMethod;
  }

  public RuntimeValue apply(RuntimeValue argument)
  { throw new UnsupportedOperationException("FIXME:");
  }
}
