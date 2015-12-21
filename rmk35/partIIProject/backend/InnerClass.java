package rmk35.partIIProject.backend;

import java.util.List;

public class InnerClass implements OutputClass
{ String name;
  StringBuilder fields;
  StringBuilder runMethod;
  List<IdentifierValue> closureVariables;
  int uniqueNumber = 0;
  int stackLimit, stackCount;
  int localLimit, localCount;

  public InnerClass(String name, List<IdentifierValue> closureVariables)
  { this(name, closureVariables, new StringBuilder(), new StringBuilder(), 0,  1);
  }
  public InnerClass(String name, List<IdentifierValue> closureVariables, StringBuilder fields, StringBuilder runMethod, int stackLimit, int localLimit)
  { this.name = name;
    this.closureVariables = closureVariables;
    this.fields = fields;
    this.runMethod = runMethod;
    this.stackLimit = stackCount = stackLimit;
    this.localLimit = localCount = localLimit;
  }

  public String uniqueID()
  { return name + "Inner" + Integer.toString(uniqueNumber);
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
      "  .limit stack  " + stackLimit + "\n" +
      "  .limit locals " + localLimit + "\n" +
      runMethod.toString() +
      "  return\n" +
      ".end method\n"
    ;
  }

  public void  incrementStackCount(int n)
  { stackCount += n;
    stackLimit = Math.max(stackLimit, stackCount);
  }
  public void decrementStackCount(int n)
  { stackCount -= n;
    if (stackCount<0) throw new InternalCompilerException("Simulated stack underflown");
  }
  public void incrementLocalLimit(int n)
  { localCount += n;
    localLimit = Math.max(localLimit, localCount);
  }
  public void decrementLocalLimit(int n)
  { localCount -= n;
    if (localCount<0) throw new InternalCompilerException("Simulated locals underflown");
  }
}
