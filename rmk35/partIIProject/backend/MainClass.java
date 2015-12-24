package rmk35.partIIProject.backend;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MainClass extends OutputClass
{ String name;
  StringBuilder fields;
  StringBuilder mainMethod;
  List<InnerClass> innerClasses;
  int uniqueNumber = 0;

  public MainClass()
  { this("anonymous");
  }
  public MainClass(String name)
  { this(name, new StringBuilder(), new StringBuilder(), new ArrayList<InnerClass>());
  }
  public MainClass(String name, StringBuilder fields, StringBuilder mainMethod, List<InnerClass> innerClasses)
  { super(1, 1); // One local for main argument
    this.name = name;
    this.fields = fields;
    this.mainMethod = mainMethod;
    this.innerClasses = innerClasses;
  }

  public void addToPrimaryMethod(String value)
  { mainMethod.append(value);
  }

  public String uniqueID()
  { uniqueNumber++;
    return "Main" + Integer.toString(uniqueNumber);
  }

  public String getAssembly()
  { return
      ".class " + name + "\n" +
      ".super java/lang/Object\n" +
      fields.toString() + "\n" +

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
  String getOutputFileName()
  { return name + ".j";
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
              (new FileWriter(getOutputFileName())))
    { writer.append(this.getAssembly());
    }
    for (OutputClass oc : innerClasses)
    { oc.saveToDisk();
    }
  }
}
