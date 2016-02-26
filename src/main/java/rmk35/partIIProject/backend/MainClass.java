package rmk35.partIIProject.backend;

import rmk35.partIIProject.runtime.TrampolineValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.GlobalIdentifierStatement;
import rmk35.partIIProject.backend.instructions.Instruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.IntegerConstantInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import rmk35.partIIProject.backend.instructions.PopInstruction;
import rmk35.partIIProject.backend.instructions.types.JVMType;
import rmk35.partIIProject.backend.instructions.types.VoidType;
import rmk35.partIIProject.backend.instructions.types.IntegerType;
import rmk35.partIIProject.backend.instructions.types.ObjectType;
import rmk35.partIIProject.backend.instructions.types.ArrayType;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MainClass extends OutputClass
{ List<InnerClass> innerClasses;
  InnerClass mainInnerClass;

  private static final JVMType voidType = new VoidType();

  public MainClass(String name)
  { this(name, new ArrayList<>());
  }
  public MainClass(String name, List<InnerClass> innerClasses)
  { super(name);
    this.innerClasses = innerClasses;
    String mainInnerClassName = getName() + "$StartLambda";
    mainInnerClass = new InnerClass(mainInnerClassName, new ArrayList<>(), 0, this);
    addInnerClass(mainInnerClass);

    ByteCodeMethod mainMethod = new ByteCodeMethod(voidType, "public static", "main", new ArrayType(new ObjectType(String.class)));
    mainMethod.addInstruction(new NewObjectInstruction(TrampolineValue.class));
    mainMethod.addInstruction(new DupInstruction());
    mainMethod.addInstruction(new NewObjectInstruction(mainInnerClassName));
    mainMethod.addInstruction(new DupInstruction());
    mainInnerClass.invokeConstructor(this, this, mainMethod);
    mainMethod.addInstruction(new NewObjectInstruction(ArrayList.class));
    mainMethod.addInstruction(new DupInstruction());
    mainMethod.addInstruction(new IntegerConstantInstruction(0));
    mainMethod.addInstruction(new NonVirtualCallInstruction(voidType, ArrayList.class.getName().replace('.', '/') + "/<init>", new IntegerType()));
    mainMethod.addInstruction(new NonVirtualCallInstruction(voidType, TrampolineValue.class.getName().replace('.', '/') + "/<init>", new ObjectType(LambdaValue.class), new ObjectType(List.class)));
    mainMethod.addInstruction(new StaticCallInstruction(new ObjectType(Object.class), TrampolineValue.class.getName().replace('.', '/') + "/bounceHelper", new ObjectType(Object.class)));
    mainMethod.addInstruction(new PopInstruction());
    methods.put("main", mainMethod);
  }

  public void addInnerClass(InnerClass innerClass)
  { innerClasses.add(innerClass);
  }

  public void addGlobalBinding(String name, Class<?> binding)
  { ByteCodeMethod initializer = getClassInitializer();
    initializer.addInstruction(new NewObjectInstruction(binding));
    initializer.addInstruction(new DupInstruction());
    initializer.addInstruction(new NonVirtualCallInstruction(voidType, binding.getName().replace('.', '/') + "/<init>"));
    GlobalIdentifierStatement destination = new GlobalIdentifierStatement(name);
    destination.ensureExistence(this, this, initializer);
    destination.generateSetOutput(this, this, initializer);
  }

  @Override
  public ByteCodeMethod getPrimaryMethod()
  { return mainInnerClass.getPrimaryMethod();
  }

  @Override
  public String getSuperClassName()
  { return Object.class.getName().replace('.', '/');
  }

  @Override
  public void saveToDisk() throws IOException
  { super.saveToDisk();
    for (OutputClass oc : innerClasses)
    { oc.saveToDisk();
    }
  }

  @Override
  public void assembleToDisk() throws Exception, IOException
  { super.assembleToDisk();
    for (OutputClass oc : innerClasses)
    { oc.assembleToDisk();
    }
  }
}
