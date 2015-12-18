package rmk35.partIIProject.backend;

import java.util.List;
import java.util.ArrayList;

public class MainClass implements OutputClass
{ String outputName;
  StringBuilder fields;
  StringBuilder mainMethod;
  List<InnerClass> innerClasses;
  int uniqueNumber = 0;

  public MainClass()
  { this("anonymous.j", new StringBuilder(), new StringBuilder(), new ArrayList<InnerClass>());
  }
  public MainClass(String outputName)
  { this(outputName, new StringBuilder(), new StringBuilder(), new ArrayList<InnerClass>());
  }
  public MainClass(String outputName, StringBuilder fields, StringBuilder mainMethod, List<InnerClass> innerClasses)
  { this.outputName = outputName;
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

  public String toString()
  { return
      ".class " + outputName + "\n" +
      ".super java/lang/Object\n" +
      fields.toString() + "\n" +

      ".method public <init>()V\n" +
      "  aload_0\n" +
      "  invokenonvirtual java/lang/Object\n" +
      "  return\n" +
      ".end method\n" +
      "\n" +
      ".method public static main([Ljava/lang/String;)V\n" +
      "  .limit stack 3\n" +
      "  .limit locals 4\n" +
      mainMethod.toString() +
      "  return\n" +
      ".end method\n"
    ;
  }
}
