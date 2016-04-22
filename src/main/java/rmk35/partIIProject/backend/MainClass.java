package rmk35.partIIProject.backend;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.TrampolineValue;
import rmk35.partIIProject.runtime.NullValue;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.instructions.Instruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.NullConstantInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import rmk35.partIIProject.backend.instructions.PopInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.stringArrayType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.lambdaValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;

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

  public MainClass(String name)
  { this(name, new ArrayList<>());
  }
  public MainClass(String name, List<InnerClass> innerClasses)
  { super(name);
    this.innerClasses = innerClasses;
    String mainInnerClassName = getName() + "$StartLambda";
    mainInnerClass = new InnerClass(mainInnerClassName, new ArrayList<>(), this, "Main inner class");
    addInnerClass(mainInnerClass);

    ByteCodeMethod mainMethod = new ByteCodeMethod(voidType, "public static", "main", stringArrayType);
    Compiler.tailCallSettings.generateCallStart(mainMethod);
    mainMethod.addInstruction(new NewObjectInstruction(mainInnerClass.getName()));
    mainMethod.addInstruction(new DupInstruction());
    mainMethod.addInstruction(new NullConstantInstruction());
    mainMethod.addInstruction(new NonVirtualCallInstruction(voidType, mainInnerClass.getName() + "/<init>", lambdaValueType));
    mainMethod.addInstruction(new NewObjectInstruction(NullValue.class));
    mainMethod.addInstruction(new DupInstruction());
    mainMethod.addInstruction(new NonVirtualCallInstruction(voidType, NullValue.class.getName().replace('.', '/') + "/<init>"));
    Compiler.tailCallSettings.generateCallEnd(mainMethod);
    Compiler.tailCallSettings.generateContinuation(mainMethod);
    mainMethod.addInstruction(new PopInstruction());
    methods.put("main", mainMethod);
  }

  public void addInnerClass(InnerClass innerClass)
  { innerClasses.add(innerClass);
  }

  public List<InnerClass> getInnerClasses()
  { return innerClasses;
  }

  public InnerClass getMainInnerClass()
  { return mainInnerClass;
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
