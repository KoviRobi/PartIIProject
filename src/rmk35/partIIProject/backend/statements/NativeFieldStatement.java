package rmk35.partIIProject.backend.statements;

import java.util.Collection;
import java.util.TreeSet;
import java.util.Map;
import java.util.List;
import java.lang.reflect.Field;
import rmk35.partIIProject.backend.OutputClass;

import lombok.ToString;

@ToString
public class NativeFieldStatement extends Statement
{ Class<?> classs;
  Field field;

  public NativeFieldStatement(String  classs, String field) throws NoSuchFieldException, ClassNotFoundException
  { this.classs =  Class.forName(classs);
    this.field =  this.classs.getField(field);
  }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod("  ; NativeFieldStatement\n");
    output.addToPrimaryMethod("  getstatic " +
      classs.getName().replaceAll("\\.", "/") + "/" + field.getName() +
       " " +
      toBinaryName(field.getType().getName()) + "\n");
    output.incrementStackCount(1);
    output.addToPrimaryMethod("\n");
  }

  static String toBinaryName(String typeName)
  { if (typeName.charAt(0) == '[')
    { return typeName.replaceAll("\\.", "/");
    } else if (typeName == "boolean")
    { return "Z";
    } else if (typeName == "byte")
    { return "B";
    } else if (typeName == "char")
    { return "C";
    } else if (typeName == "double")
    { return "D";
    } else if (typeName == "float")
    { return "F";
    } else if (typeName == "int")
    { return "I";
    } else if (typeName == "long")
    { return "J";
    } else if (typeName == "short")
    { return "S";
    } else if (typeName == "void")
    { return "V";
    } else
    { return "L" + typeName.replaceAll("\\.", "/") + ";";
    }
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}