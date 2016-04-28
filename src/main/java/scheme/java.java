package scheme;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.ObjectValue;
import rmk35.partIIProject.runtime.ClassValue;
import rmk35.partIIProject.runtime.ValueHelper;
import rmk35.partIIProject.runtime.libraries.BinaryLambda;
import rmk35.partIIProject.runtime.libraries.UnaryLambda;
import rmk35.partIIProject.runtime.libraries.SyntaxBindingCreator;
import rmk35.partIIProject.runtime.libraries.ReflectiveEnvironment;

import rmk35.partIIProject.middle.bindings.Binding;

public class java extends ReflectiveEnvironment
{ public java() { bind(); }
  public static RuntimeValue clas$000073 =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue name)
    { try
      { return ValueHelper.toSchemeValue(Class.forName(((IdentifierValue) name).getValue()));
      } catch (ClassNotFoundException exception)
      { throw new RuntimeException("Class \"" + ((IdentifierValue) name).getValue() + "\" not found.", exception);
      }
    }
  };

  public static RuntimeValue field =
  new BinaryLambda()
  { @Override
    public RuntimeValue run2(RuntimeValue objectValue, RuntimeValue fieldName)
    { try
      { Object object = ((ObjectValue) objectValue).toJavaValue();
        String fieldNameString = ((IdentifierValue) fieldName).getValue();
        return ValueHelper.toSchemeValue(object.getClass().getField(fieldNameString).get(object));
      } catch (NoSuchFieldException | IllegalAccessException exception)
      { throw new RuntimeException(exception);
      }
    }
  };

  public static RuntimeValue static_field =
  new BinaryLambda()
  { @Override
    public RuntimeValue run2(RuntimeValue classValue, RuntimeValue fieldName)
    { try
      { Class<?> javaClass = ((ClassValue) classValue).toJavaValue();
        String fieldNameString = ((IdentifierValue) fieldName).getValue();
        return ValueHelper.toSchemeValue(javaClass.getField(fieldNameString).get(null));
      } catch (NoSuchFieldException | IllegalAccessException exception)
      { throw new RuntimeException(exception);
      }
    }
  };

  public static Binding multi_despatch = SyntaxBindingCreator.create
  (Compiler.baseEnvironment
  , "(_ object (message arguments ...) ...)"
  , "(let ((evaluated-object object)) (begin (evaluated-object message arguments ...) ...))");
}
