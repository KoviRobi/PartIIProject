package rmk35.partIIProject.backend;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.bindings.Binding;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.PutFieldInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.PopInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.lambdaValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.integerType;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class InnerClass extends OutputClass
{ String comment;

  public InnerClass(List<String> packageName, String name, EnvironmentValue environment, List<IdentifierValue> formals, IdentifierValue improperFormalOrNull, MainClass mainClass, String comment)
  { super(makeFullName(packageName, name));
    this.comment = comment;

    // Ensure closure variables exist and also that they are set on construction
    ByteCodeMethod initializerMethod = new ByteCodeMethod(/* jumps */ false, voidType, "public", "<init>", lambdaValueType);
    initializerMethod.addInstruction(new CommentPseudoInstruction("Inner class, comment: " + comment));
    initializerMethod.addInstruction(new LocalLoadInstruction(runtimeValueType, 0));
    initializerMethod.addInstruction(new NonVirtualCallInstruction(voidType, getSuperClassName() + "/<init>"));
    // Store parent into LambdaValue.parent
    initializerMethod.addInstruction(new LocalLoadInstruction(runtimeValueType, 0));
    initializerMethod.addInstruction(new LocalLoadInstruction(runtimeValueType, 1));
    initializerMethod.addInstruction(new PutFieldInstruction(lambdaValueType, LambdaValue.class, "parent"));
    // Overwrite initializer
    methods.put("<init>", initializerMethod);

    ByteCodeMethod runMethod = new ByteCodeMethod(/* jumps */ true, runtimeValueType, "public", "run", runtimeValueType);
    // Store values into local variables
    runMethod.addInstruction(new LocalLoadInstruction(runtimeValueType, 1));
    for (IdentifierValue  formal : formals)
    { runMethod.addInstruction(new CheckCastInstruction(ConsValue.class));
      runMethod.addInstruction(new DupInstruction()); // Current head
      runMethod.addInstruction(new VirtualCallInstruction(runtimeValueType, ConsValue.class, "getCar"));
      IdentifierStatement formalStatement = (IdentifierStatement) environment.addLocalVariable(this, formal.getValue()).toStatement(null);
      formalStatement.ensureExistence(mainClass, this, runMethod);
      formalStatement.generateSetOutput(mainClass, this, runMethod);
      runMethod.addInstruction(new VirtualCallInstruction(runtimeValueType, ConsValue.class, "getCdr"));
    }
    if (improperFormalOrNull != null)
    {  IdentifierStatement formalStatement = (IdentifierStatement) environment.addLocalVariable(this, improperFormalOrNull.getValue()).toStatement(null);
      formalStatement.ensureExistence(mainClass, this, runMethod);
      formalStatement.generateSetOutput(mainClass, this, runMethod);
    } else
    { runMethod.addInstruction(new CheckCastInstruction(NullValue.class));
      runMethod.addInstruction(new PopInstruction()); // Pop null
    }
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
  { method.addInstruction(new LocalLoadInstruction(runtimeValueType, 0));
    method.addInstruction(new NonVirtualCallInstruction(voidType, this.getName() + "/<init>", lambdaValueType));
  }

  public static List<String> makeFullName(List<String> packagePart, String name)
  { List<String> returnValue = new ArrayList<>();
    for (String part : packagePart)
    { returnValue.add(part);
    }
    returnValue.add(name);
    return returnValue;
  }
}
