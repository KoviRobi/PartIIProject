package scheme;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ValueHelper;
import rmk35.partIIProject.runtime.libraries.NullaryLambda;
import rmk35.partIIProject.runtime.libraries.ReflectiveEnvironment;

public class time extends ReflectiveEnvironment
{ public time() { bind(); }
  public static RuntimeValue current$00002Djiffy =
  new NullaryLambda()
  { @Override
    public RuntimeValue run0()
    { return ValueHelper.toSchemeValue(System.nanoTime());
    }
  };

  public static RuntimeValue current$00002Dsecond =
  new NullaryLambda()
  { @Override
    public RuntimeValue run0()
    { return ValueHelper.toSchemeValue(System.currentTimeMillis()/1000);
    }
  };

  public static RuntimeValue jiffies$00002Dper$00002Dsecond =
  new NullaryLambda()
  { @Override
    public RuntimeValue run0()
    { return ValueHelper.toSchemeValue(1000*1000*1000);
    }
  };
}
