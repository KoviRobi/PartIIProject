package rmk35.partIIProject.backend;

import java.util.List;

public class InnerClass implements OutputClass
{ String name;
  StringBuilder fields;
  StringBuilder runMethod;
  List<IdentifierValue> closureVariables;
  int uniqueNumber = 0;

  public InnerClass(String name, List<IdentifierValue> closureVariables)
  { this(name, closureVariables, new StringBuilder(), new StringBuilder());
  }
  public InnerClass(String name, List<IdentifierValue> closureVariables, StringBuilder fields, StringBuilder runMethod)
  { this.name = name;
    this.closureVariables = closureVariables;
    this.fields = fields;
    this.runMethod = runMethod;
  }

  public String uniqueID()
  { return "Inner" + name + Integer.toString(uniqueNumber);
  }

  public void addToPrimaryMethod(String value)
  { runMethod.append(value);
  }

  public String toString()
  { return
      ".class " + name + "\n" +
      ".super java/lang/Object\n" +
      fields.toString() + "\n" +

      ".method public <init>()V\n" +
      "  aload_0\n" +
      "  invokenonvirtual java/lang/Object\n" +
      "  return\n" +
      ".end method\n" +

      ".method public static main([Ljava/lang/String;)V\n" +
      "  .limit stack 3\n" +
      "  .limit locals 4\n" +
      runMethod.toString() +
      "  return\n" +
      ".end method\n"
    ;
  }
}
