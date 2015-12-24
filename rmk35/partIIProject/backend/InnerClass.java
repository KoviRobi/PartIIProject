package rmk35.partIIProject.backend;

import java.util.List;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

public class InnerClass extends OutputClass
{ String name;
  StringBuilder fields;
  StringBuilder runMethod;
  List<IdentifierValue> closureVariables;
  int uniqueNumber = 0;
  MainClass mainClass;

  public InnerClass(String name, List<IdentifierValue> closureVariables, MainClass mainClass)
  { this(name, closureVariables, new StringBuilder(), new StringBuilder(), mainClass);
  }
  public InnerClass(String name, List<IdentifierValue> closureVariables, StringBuilder fields, StringBuilder runMethod, MainClass mainClass)
  { super(1, 2); // One local for 'this' and one for argument
    this.name = name;
    this.closureVariables = closureVariables;
    this.fields = fields;
    this.runMethod = runMethod;
    this.mainClass = mainClass;
  }

  @Override
  public String uniqueID()
  { return name + "Inner" + Integer.toString(uniqueNumber);
  }

  @Override
  public void addToPrimaryMethod(String value)
  { runMethod.append(value);
  }

  @Override
  public String getAssembly()
  { return
      ".class " + name + "\n" +
      ".super rmk35/partIIProject/backend/runtimeValues/LambdaValue\n" +
      fields.toString() + "\n" +

      ".method public <init>()V\n" +
      "  aload_0\n" +
      "  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/LambdaValue/<init>()V\n" +
      "  return\n" +
      ".end method\n" +
      "\n" +
      ".method public run(Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;)Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;\n" +
      "  .limit stack  " + stackLimit + "\n" +
      "  .limit locals " + localLimit + "\n" +
     runMethod.toString() +
      "  areturn\n" +
      ".end method\n"
    ;
  }

  @Override
  public String getOutputFileName()
  { return name + ".j";
  }

  @Override
  public MainClass getMainClass()
  {  return mainClass;
  }
}
