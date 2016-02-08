package rmk35.partIIProject.backend;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.ClosureIdentifierStatement;
import rmk35.partIIProject.backend.statements.LocalIdentifierStatement;
import rmk35.partIIProject.backend.statements.DefineStatement;
import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.backend.instructions.Instruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
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

  public InnerClass(String name, List<IdentifierStatement> closureVariables, int variableCount, MainClass mainClass)
  { super(name);
    this.closureVariables = closureVariables;
    this.variableCount = variableCount;

    constructorTypes = new ObjectType[closureVariables.size()];
    Arrays.fill(constructorTypes, runtimeValueType);
    // Ensure closure variables exist and also that they are set on construction
    ByteCodeMethod initializerMethod = new ByteCodeMethod(voidType, "public", "<init>", constructorTypes);
    initializerMethod.addInstruction(new LocalLoadInstruction(new ObjectType(), 0));
    initializerMethod.addInstruction(new NonVirtualCallInstruction(voidType, getSuperClassName() + "/<init>"));
    int index = 1; // 0 is 'this'
    for (IdentifierStatement closureName : closureVariables)
    { String identifierName = closureName.getName();
      Statement storeStatement = new DefineStatement(new ClosureIdentifierStatement(identifierName), new LocalIdentifierStatement(identifierName, index));
      storeStatement.generateOutput(mainClass, this, initializerMethod);
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
