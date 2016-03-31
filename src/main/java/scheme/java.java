package scheme;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.ObjectValue;
import rmk35.partIIProject.runtime.ClassValue;
import rmk35.partIIProject.runtime.ValueHelper;
import rmk35.partIIProject.runtime.libraries.BinaryLambda;
import rmk35.partIIProject.runtime.libraries.UnaryLambda;

public class java extends ReflectiveEnvironment
{ public java() { bind(); }
  public RuntimeValue clasX000073 =
  new UnaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue name)
    { try
      { return ValueHelper.toSchemeValue(Class.forName(((IdentifierValue) name).getValue()));
      } catch (ClassNotFoundException exception)
      { throw new RuntimeException("Class \"" + ((IdentifierValue) name).getValue() + "\" not found.", exception);
      }
    }
  };

  public RuntimeValue field =
  new BinaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue objectValue, RuntimeValue fieldName)
    { try
      { Object object = ((ObjectValue) objectValue).toJavaValue();
        String fieldNameString = ((IdentifierValue) fieldName).getValue();
        return ValueHelper.toSchemeValue(object.getClass().getField(fieldNameString).get(object));
      } catch (NoSuchFieldException | IllegalAccessException exception)
      { throw new RuntimeException(exception);
      }
    }
  };

  public RuntimeValue staticX00002Dfield =
  new BinaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue classValue, RuntimeValue fieldName)
    { try
      { Class<?> javaClass = ((ClassValue) classValue).toJavaValue();
        String fieldNameString = ((IdentifierValue) fieldName).getValue();
        return ValueHelper.toSchemeValue(javaClass.getField(fieldNameString).get(null));
      } catch (NoSuchFieldException | IllegalAccessException exception)
      { throw new RuntimeException(exception);
      }
    }
  };
}
