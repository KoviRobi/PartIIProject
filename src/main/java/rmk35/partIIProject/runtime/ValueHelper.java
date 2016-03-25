package rmk35.partIIProject.runtime;

import java.util.List;
import java.util.ListIterator;
import java.lang.reflect.Method;

public class ValueHelper
{ public static RuntimeValue toSchemeValue(Object value)
  { if (value instanceof RuntimeValue)
    { return (RuntimeValue) value;
    } else if (value instanceof Boolean)
    { return new BooleanValue((Boolean) value);
    } else if (value instanceof Integer)
    { return new NumberValue((Integer) value);
    } else if (value instanceof String)
    { return new StringValue((String) value);
    } else if (value instanceof Character)
    { return new CharacterValue((Character) value);
    } else if (value instanceof List)
    { ListIterator<Object> iterator = ((List) value).listIterator(((List) value).size());
      RuntimeValue returnValue = new NullValue();
      while (iterator.hasPrevious())
      { RuntimeValue item = toSchemeValue(iterator.previous()); // ToDo circular data structures
        returnValue = new ConsValue(item, returnValue);
      }
      return returnValue;
    } else if (value instanceof Object[])
    { Object[] objectArray = (Object[]) value;
      RuntimeValue[] array = new RuntimeValue[objectArray.length];
      for (int i = 0; i < objectArray.length; i++)
      { array[i] = toSchemeValue(objectArray[i]);
      }
      return new VectorValue(array);
    } else if (value instanceof Method)
    { return new MethodValue((Method) value);
    } else if (value instanceof Class)
    { return new ClassValue((Class) value);
    } else
    { return new ObjectValue(value);
    }
  }
}