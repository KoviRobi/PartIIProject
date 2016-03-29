package rmk35.partIIProject.backend;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.ConsValue;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.ClosureIdentifierStatement;
import rmk35.partIIProject.backend.statements.LocalIdentifierStatement;
import rmk35.partIIProject.backend.statements.DefineStatement;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.Instruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.LocalStoreInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.PopInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.JVMType;
import rmk35.partIIProject.backend.instructions.types.ObjectType;
import rmk35.partIIProject.backend.instructions.types.VoidType;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class InnerClass extends OutputClass
{ List<IdentifierStatement> closureVariables;
  int variableCount;
  String comment;

  private ObjectType[] constructorTypes;

  private static final JVMType voidType = new VoidType();
  private static final ObjectType runtimeValueType = new ObjectType(RuntimeValue.class);

  public InnerClass(String name, List<IdentifierStatement> closureVariables, int variableCount, MainClass mainClass, String comment)
  { super(name);
    this.closureVariables = closureVariables;
    this.variableCount = variableCount;
    this.comment = comment;

    constructorTypes = new ObjectType[closureVariables.size()];
    Arrays.fill(constructorTypes, runtimeValueType);
    // Ensure closure variables exist and also that they are set on construction
    ByteCodeMethod initializerMethod = new ByteCodeMethod(voidType, "public", "<init>", constructorTypes);
    initializerMethod.addInstruction(new CommentPseudoInstruction("Inner class, comment: " + comment));
    initializerMethod.addInstruction(new LocalLoadInstruction(runtimeValueType, 0));
    initializerMethod.addInstruction(new NonVirtualCallInstruction(voidType, getSuperClassName() + "/<init>"));
    int index = 1; // 0 is 'this'
    for (IdentifierStatement closureName : closureVariables)
    { String identifierName = closureName.getName();
      new DefineStatement(new ClosureIdentifierStatement(identifierName), new LocalIdentifierStatement(identifierName, index)).generateOutput(mainClass, this, initializerMethod);
      // DefineStatement returns an unspecified value
      initializerMethod.addInstruction(new PopInstruction());
      index++;
    }
    // Overwrite initializer
    methods.put("<init>", initializerMethod);

    ByteCodeMethod applyMethod = new ByteCodeMethod(runtimeValueType, "public", "apply", runtimeValueType);
    // Store array into locals
    // Load arguments, may be over written (unless variable count is 0, which may happen if we got null, or this was a "(lambda x body...)")
    applyMethod.addInstruction(new LocalLoadInstruction(runtimeValueType, 1));
    for (int i = 0; i < variableCount; i++)
    { applyMethod.addInstruction(new CheckCastInstruction(ConsValue.class));
      applyMethod.addInstruction(new DupInstruction()); // Current head
      applyMethod.addInstruction(new VirtualCallInstruction(runtimeValueType, ConsValue.class.getName().replace('.', '/') + "/getCar"));
      applyMethod.ensureLocal(i+1);
      applyMethod.addInstruction(new LocalStoreInstruction(runtimeValueType, i+1)); // i+1, as local 0 is this, which we can't over write
      applyMethod.addInstruction(new VirtualCallInstruction(runtimeValueType, ConsValue.class.getName().replace('.', '/') + "/getCdr"));
    }
    // ToDo improper lists
    applyMethod.addInstruction(new CheckCastInstruction(NullValue.class));
    applyMethod.addInstruction(new PopInstruction()); // Pop null
    methods.put("apply", applyMethod);
  }

  @Override
  public String getSuperClassName()
  { return LambdaValue.class.getName().replace('.', '/');
  }

  @Override
  public ByteCodeMethod getPrimaryMethod()
  { return methods.get("apply");
  }

  public void invokeConstructor(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { for (IdentifierStatement identifier : closureVariables)
    { identifier.generateOutput(mainClass, outputClass, method);
    }
    method.addInstruction(new NonVirtualCallInstruction(voidType, this.getName() + "/<init>", constructorTypes));
  }
}
