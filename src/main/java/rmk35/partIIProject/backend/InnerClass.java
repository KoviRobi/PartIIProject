package rmk35.partIIProject.backend;

import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.runtimeValues.RuntimeValue;
import rmk35.partIIProject.backend.runtimeValues.LambdaValue;
import rmk35.partIIProject.backend.instructions.Instruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
import rmk35.partIIProject.backend.instructions.PutFieldInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.IntegerConstantInstruction;
import rmk35.partIIProject.backend.instructions.InterfaceCallInstruction;
import rmk35.partIIProject.backend.instructions.LocalStoreInstruction;
import rmk35.partIIProject.backend.instructions.PopInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.JVMType;
import rmk35.partIIProject.backend.instructions.types.ObjectType;
import rmk35.partIIProject.backend.instructions.types.VoidType;
import rmk35.partIIProject.backend.instructions.types.IntegerType;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class InnerClass extends OutputClass
{ List<IdentifierStatement> closureVariables;
  int variableCount;

  private ObjectType[] constructorTypes;

  private static final JVMType voidType = new VoidType();
  private static final ObjectType runtimeValueType = new ObjectType(RuntimeValue.class);
  private static final ObjectType listType = new ObjectType(List.class);
  private static final ObjectType objectType = new ObjectType(Object.class);

  public InnerClass(String name, List<IdentifierStatement> closureVariables, int variableCount)
  { super(name);
    this.closureVariables = closureVariables;
    this.variableCount = variableCount;

    constructorTypes = new ObjectType[closureVariables.size()];
    Arrays.fill(constructorTypes, runtimeValueType);
    // Ensure closure variables exist and also that they are set on construction
    ByteCodeMethod initializerMethod = new ByteCodeMethod(voidType, "public", "<init>", constructorTypes);; // FIXME: arguments to initializer!
    initializerMethod.addInstruction(new LocalLoadInstruction(new ObjectType(), 0));
    initializerMethod.addInstruction(new NonVirtualCallInstruction(voidType, getSuperClassName() + "/<init>"));
    int index = 1; // 0 is 'this'
    for (IdentifierStatement identifier : closureVariables)
    { ensureFieldExists("private", identifier.getName(), runtimeValueType);
      initializerMethod.ensureLocal(index);
      initializerMethod.addInstruction(new LocalLoadInstruction(runtimeValueType, 0)); // This
      initializerMethod.addInstruction(new LocalLoadInstruction(runtimeValueType, index)); // Value
      initializerMethod.addInstruction(new PutFieldInstruction(runtimeValueType, getName() + "/" + identifier.getName()));
      index++;
    }
    // Overwrite initializer
    methods.put("<init>", initializerMethod);

    ByteCodeMethod runMethod = new ByteCodeMethod(runtimeValueType, "public", "run", listType);
    // Store array into locals
    runMethod.addInstruction(new LocalLoadInstruction(listType, 1)); // Load ArrayList, will be over written
    for (int i = 0; i < variableCount; i++)
    { runMethod.addInstruction(new DupInstruction()); // Loop invariant: ArrayList on top of the stack
      runMethod.addInstruction(new IntegerConstantInstruction(i));
      runMethod.addInstruction(new InterfaceCallInstruction(/* static */ false, objectType, "java/util/List/get", new IntegerType()));
      runMethod.ensureLocal(i+1);
      runMethod.addInstruction(new LocalStoreInstruction(objectType, i+1)); // i+1, as local 0 is this, which we can't over write
    }
    runMethod.addInstruction(new PopInstruction()); // Pop ArrayList
    methods.put("run", runMethod);
  }

  @Override
  public String getSuperClassName()
  { return LambdaValue.class.getName().replace('.', '/');
  }

  @Override
  public ByteCodeMethod getPrimaryMethod()
  { return methods.get("run");
  }

  public void invokeConstructor(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { for (IdentifierStatement identifier : closureVariables)
    { identifier.generateOutput(mainClass, outputClass, method);
    }
    method.addInstruction(new NonVirtualCallInstruction(voidType, this.getName() + "/<init>", constructorTypes));
  }
}
