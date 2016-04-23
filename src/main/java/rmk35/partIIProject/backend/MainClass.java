package rmk35.partIIProject.backend;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.ApplicationStatement;
import rmk35.partIIProject.backend.statements.SequenceStatement;
import rmk35.partIIProject.backend.statements.InstructionStatement;
import rmk35.partIIProject.backend.instructions.Instruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.NullConstantInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
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

    ByteCodeMethod mainMethod = new ByteCodeMethod(/* jumps */ false, voidType, "public static", "main", stringArrayType);
    new ApplicationStatement
      (new SequenceStatement
        (new InstructionStatement(new NewObjectInstruction(mainInnerClass.getName())),
        new InstructionStatement(new DupInstruction()),
        new InstructionStatement(new NullConstantInstruction()),
        new InstructionStatement(new NonVirtualCallInstruction(voidType, mainInnerClass.getName() + "/<init>", lambdaValueType))))
      .generateOutput(this, this, mainMethod);
    Compiler.tailCallSettings.generateStart(mainMethod);
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
