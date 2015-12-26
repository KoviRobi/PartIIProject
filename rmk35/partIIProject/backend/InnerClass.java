package rmk35.partIIProject.backend;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.runtimeValues.IdentifierValue;

public class InnerClass extends OutputClass
{ String name;
  Set<String> fields;
  StringBuilder runMethod;
  List<IdentifierStatement> closureVariables;
  int uniqueNumber = 0;
  MainClass mainClass;

  public InnerClass(String name, List<IdentifierStatement> closureVariables, MainClass mainClass)
  { this(name, closureVariables, new HashSet<String>(), new StringBuilder(), mainClass);
  }
  public InnerClass(String name, List<IdentifierStatement> closureVariables, Set<String> fields, StringBuilder runMethod, MainClass mainClass)
  { super(0, 2); // One local for 'this' and one for argument
    this.name = name;
    this.closureVariables = closureVariables;
    this.fields = fields;
    this.runMethod = runMethod;
    this.mainClass = mainClass;

    for (IdentifierStatement identifier : closureVariables)
    { ensureFieldExists("private", identifier.getName(), "Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;");
    }
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
  public void ensureFieldExists(String modifier, String name, String type)
  { fields.add(".field " + modifier + " " + name + " " + type);
  }

  @Override
  public String getAssembly()
  { return
      ".class " + name + "\n" +
      ".super rmk35/partIIProject/backend/runtimeValues/LambdaValue\n" +
      String.join("\n", fields) + "\n\n" +

      // TODO: this is ugly (filling in closure variables)
      ".method public <init>(" + constructorTypes(closureVariables) + ")V\n" +
      (fields.size() == 0? "" : "  .limit locals " + (fields.size() + 1) + "\n  .limit stack 2\n") +
      "  aload_0\n" +
      "  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/LambdaValue/<init>()V\n" +
      storeClosure(closureVariables) +
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

  private String constructorTypes(List<IdentifierStatement> identifiers)
  { StringBuilder returnValue = new StringBuilder();
    for (IdentifierStatement identifier : identifiers)
    { returnValue.append("Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;");
    }
    return returnValue.toString();
  }
  
  private String storeClosure(List<IdentifierStatement> identifiers)
  { StringBuilder returnValue = new StringBuilder();
    int i = 1;
    for (IdentifierStatement identifier : identifiers)
    { returnValue.append("  aload_0\n");
      if (i < 4)
      { returnValue.append("  aload_" + i + "\n");
      } else
      { returnValue.append("  aload " + i + "\n");
      }
      returnValue.append("  putfield " +  getName() + "/" + identifier.getName() + " " + "Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;\n");
      i++;
    }
    return returnValue.toString();
  }

  @Override
  public String getName()
  { return name;
  }

  @Override
  public MainClass getMainClass()
  { return mainClass;
  }

  public void invokeConstructor(Map<IdentifierValue, Definition> definitions,
                                Map<IdentifierValue, Macro> macros,
                                OutputClass output)
  { for (IdentifierStatement identifier : closureVariables)
    { identifier.generateOutput(definitions, macros, output);
    }
    output.addToPrimaryMethod("  invokenonvirtual " + getName() + "/<init>(" + constructorTypes(closureVariables) + ")V\n");

  }
}
