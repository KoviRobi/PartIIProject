package rmk35.partIIProject.backend;

import java.util.List;
import java.util.ArrayList;

public class MainClass implements OutputClass
{ String outputName;
  StringBuilder fields;
  StringBuilder mainMethod;
  List<InnerClass> innerClasses;
  int uniqueNumber = 0;
  int stackLimit, stackCount;
  int localLimit, localCount;

  public MainClass()
  { this("anonymous.j");
  }
  public MainClass(String outputName)
  { this(outputName, new StringBuilder(), new StringBuilder(), new ArrayList<InnerClass>(), 0, 0);
  }
  public MainClass(String outputName, StringBuilder fields, StringBuilder mainMethod, List<InnerClass> innerClasses, int stackLimit, int localLimit)
  { this.outputName = outputName;
    this.fields = fields;
    this.mainMethod = mainMethod;
    this.innerClasses = innerClasses;
    this.stackLimit = stackLimit;
    this.localLimit = localLimit;
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
      "  .limit stack " + stackLimit + "\n" +
      "  .limit locals " + localLimit + "\n" +
      mainMethod.toString() +
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
