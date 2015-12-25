package rmk35.partIIProject.backend;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MainClass extends OutputClass
{ String name;
  Set<String> fields;
  StringBuilder mainMethod;
  List<InnerClass> innerClasses;
  int uniqueNumber = 0;

  public MainClass()
  { this("anonymous");
  }
  public MainClass(String name)
  { this(name, new HashSet<String>(), new StringBuilder(), new ArrayList<InnerClass>());
  }
  public MainClass(String name, Set<String> fields, StringBuilder mainMethod, List<InnerClass> innerClasses)
  { super(0, 1); // One local for main argument
    this.name = name;
    this.fields = fields;
    this.mainMethod = mainMethod;
    this.innerClasses = innerClasses;
  }

  @Override
  public void addToPrimaryMethod(String value)
  { mainMethod.append(value);
  }

  @Override
  public void ensureFieldExists(String modifier, String name, String type)
  { fields.add(".field " + modifier + " " + name + " " + type);
  }

  @Override
  public String uniqueID()
  { uniqueNumber++;
    return "Main" + Integer.toString(uniqueNumber);
  }

  @Override
  public String getAssembly()
  { return
      ".class " + name + "\n" +
      ".super java/lang/Object\n" +
      String.join("\n", fields) + "\n" +

      ".method public <init>()V\n" +
      "  aload_0\n" +
      "  invokenonvirtual java/lang/Object/<init>()V\n" +
      "  return\n" +
      ".end method\n" +
      "\n" +
      ".method public static main([Ljava/lang/String;)V\n" +
      "  .limit stack " + stackLimit + "\n" +
      "  .limit locals " + localLimit + "\n" +
      mainMethod.toString() +
      "  return\n" +
      ".end method\n"
    ;
  }

  @Override
  public String getName()
  { return name;
  }

  @Override
  public MainClass getMainClass()
  { return this;
  }

  public void addInnerClass(InnerClass innerClass)
  { innerClasses.add(innerClass);
  }

  @Override
  public void saveToDisk() throws IOException
  { try (BufferedWriter writer =
            new BufferedWriter
              (new FileWriter(getName() + ".j")))
    { writer.append(this.getAssembly());
    }
    for (OutputClass oc : innerClasses)
    { oc.saveToDisk();
    }
  }
}
