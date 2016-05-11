package rmk35.partIIProject.backend;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.NullValue;

import rmk35.partIIProject.backend.statements.Statement;
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
import java.util.Arrays;

public class MainClass extends OutputClass
{ List<InnerClass> innerClasses;
  List<LibraryClass> libraries;
  InnerClass mainInnerClass;

  public MainClass(String... fullName)
  { this(Arrays.asList(fullName), new ArrayList<>(), new ArrayList<>());
  }
  public MainClass(List<String> fullName)
  { this(fullName, new ArrayList<>(), new ArrayList<>());
  }
  public MainClass(List<String> fullName, List<InnerClass> innerClasses, List<LibraryClass> libraries)
  { super(fullName);
    this.innerClasses = innerClasses;
    this.libraries = libraries;
    String mainInnerClassName = getClassName() + "$StartLambda";
    mainInnerClass = new InnerClass(getPackage(), mainInnerClassName, null, new ArrayList<>(), null, this, "Main inner class", false);
    addInnerClass(mainInnerClass);

    ByteCodeMethod mainMethod = new ByteCodeMethod(/* jumps */ false, voidType, "public static", "main", stringArrayType);
    Compiler.tailCallSettings.generateStartStart(mainMethod);
    mainMethod.addInstruction(new NewObjectInstruction(mainInnerClass.getName()));
    mainMethod.addInstruction(new DupInstruction());
    mainMethod.addInstruction(new NullConstantInstruction());
    mainMethod.addInstruction(new NonVirtualCallInstruction(voidType, getMainInnerClass().getName() + "/<init>", lambdaValueType));
    mainMethod.addInstruction(new NewObjectInstruction(NullValue.class));
    mainMethod.addInstruction(new DupInstruction());
    mainMethod.addInstruction(new NonVirtualCallInstruction(voidType, NullValue.class, "<init>"));
    Compiler.tailCallSettings.generateStartEnd(mainMethod);
    mainMethod.addInstruction(new PopInstruction());
    methods.put("main", mainMethod);
  }

  public void addInnerClass(InnerClass innerClass)
  { innerClasses.add(innerClass);
  }

  public void addLibrary(LibraryClass library)
  { libraries.add(library);
  }

  public List<InnerClass> getInnerClasses()
  { return innerClasses;
  }

  public InnerClass getMainInnerClass()
  { return mainInnerClass;
  }

  @Override
  public ByteCodeMethod getPrimaryMethod()
  { return getMainInnerClass().getPrimaryMethod();
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
    for (OutputClass oc : libraries)
    { oc.saveToDisk();
    }
  }

  @Override
  public void assembleToDisk() throws Exception, IOException
  { super.assembleToDisk();
    for (OutputClass oc : innerClasses)
    { oc.assembleToDisk();
    }
    for (OutputClass oc : libraries)
    { oc.assembleToDisk();
    }
  }

  public boolean isInternal()
  { return false;
  }
}
