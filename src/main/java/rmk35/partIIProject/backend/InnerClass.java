package rmk35.partIIProject.backend;

import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.runtimeValues.RuntimeValue;
import rmk35.partIIProject.backend.instructions.Instruction;
import rmk35.partIIProject.backend.instructions.BeginMethodDirective;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.JVMType;
import rmk35.partIIProject.backend.instructions.types.ObjectType;
import rmk35.partIIProject.backend.instructions.types.VoidType;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class InnerClass extends OutputClass
{ String name;
  Set<String> fields;
  List<String> runMethod;
  List<IdentifierStatement> closureVariables;
  int uniqueNumber = 0;
  MainClass mainClass;
  int variableCount;

  private ObjectType[] constructorTypes;

  private static final ObjectType runtimeValueType = new ObjectType(RuntimeValue.class);

  public InnerClass(String name, List<IdentifierStatement> closureVariables, MainClass mainClass, int variableCount)
  { this(name, closureVariables, new HashSet<String>(), new ArrayList<>(), mainClass, variableCount);
  }
  public InnerClass(String name, List<IdentifierStatement> closureVariables, Set<String> fields, List<String> runMethod, MainClass mainClass, int variableCount)
  { super(3, 1 + variableCount); // One local for 'this' and then arguments. Three stack for storeArrayIntoLocals
    this.name = name;
    this.closureVariables = closureVariables;
    this.fields = fields;
    this.runMethod = runMethod;
    this.mainClass = mainClass;
    this.variableCount = variableCount;

    for (IdentifierStatement identifier : closureVariables)
    { ensureFieldExists("private", identifier.getName(), runtimeValueType);
    }
    constructorTypes = new ObjectType[closureVariables.size()];
    Arrays.fill(constructorTypes, runtimeValueType);
  }

  @Override
  public String uniqueID()
  { return name + "Inner" + Integer.toString(uniqueNumber);
  }

  @Override
  public void addInstruction(Instruction instruction)
  { runMethod.add(instruction.byteCode());
  }

  @Override
  public void ensureFieldExists(String modifier, String name, JVMType type)
  { fields.add(".field " + modifier + " " + name + " " + type.toString());
  }

  @Override
  public String getAssembly() // NEXT: make local and stack limit per method, not per class
  { return
      ".class " + name + "\n" +
      ".super rmk35/partIIProject/backend/runtimeValues/LambdaValue\n" +
      String.join("\n", fields) + "\n\n" +

      // TODO: this is ugly (filling in closure variables)
      (new BeginMethodDirective(new VoidType(), "<init>", constructorTypes)).byteCode() + "\n" +
      (fields.size() == 0? "" : "  .limit locals " + (fields.size() + 1) + "\n  .limit stack 2\n") +
      "  aload_0\n" +
      "  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/LambdaValue/<init>()V\n" +
      storeClosure(closureVariables) +
      "  return\n" +
      ".end method\n" +
      "\n" +
      ".method public run(Ljava/util/ArrayList;)Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;\n" +
      "  .limit stack  " + stackLimit + "\n" +
      "  .limit locals " + localLimit + "\n" +
      storeArrayIntoLocals() +
      String.join("\n", runMethod) + "\n" +
      "  areturn\n" +
      ".end method\n"
    ;
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

  public void invokeConstructor(OutputClass output)
  { for (IdentifierStatement identifier : closureVariables)
    { identifier.generateOutput(output);
    }
    output.addToPrimaryMethod(new NonVirtualCallInstruction(new VoidType(), getName() + "/<init>", constructorTypes));
  }

  public String storeArrayIntoLocals()
  { StringBuilder returnValue = new StringBuilder();
    returnValue.append("  aload_1\n"); // Array
    for (int i = 0; i < variableCount; i++)
    { returnValue.append("  dup\n");
      if (i < 6)
      { returnValue.append("  iconst_" + i + "\n");
      } else
      { returnValue.append("  ldc " + i + "\n");
      }
      returnValue.append("  invokeinterface java/util/List/get(I)Ljava/lang/Object; 2\n");
      if (i < 4)
      { returnValue.append("  astore_" + (i + 1) + "\n");
      } else
      { returnValue.append("  astore " + (i + 1) + "\n");
      }
    }
    returnValue.append("  pop\n"); // Pop array
    return returnValue.toString();
  }
}
