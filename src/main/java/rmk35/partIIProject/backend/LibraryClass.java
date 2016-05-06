package rmk35.partIIProject.backend;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.libraries.ReflectiveEnvironment;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.instructions.Instruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.NullConstantInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.PopInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.stringArrayType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.lambdaValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.objectType;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LibraryClass extends MainClass
{ List<InnerClass> innerClasses;

  public LibraryClass(List<String> packageAndName)
  { super(packageAndName);

    ByteCodeMethod classConstructor = new ByteCodeMethod(/* jumps */ false, voidType, "public static", "<clinit>");
    Compiler.tailCallSettings.generateStartStart(classConstructor);
    classConstructor.addInstruction(new NewObjectInstruction(mainInnerClass.getName()));
    classConstructor.addInstruction(new DupInstruction());
    classConstructor.addInstruction(new NullConstantInstruction());
    classConstructor.addInstruction(new NonVirtualCallInstruction(voidType, getMainInnerClass().getName() + "/<init>", lambdaValueType));
    classConstructor.addInstruction(new NewObjectInstruction(NullValue.class));
    classConstructor.addInstruction(new DupInstruction());
    classConstructor.addInstruction(new NonVirtualCallInstruction(voidType, NullValue.class, "<init>"));
    Compiler.tailCallSettings.generateStartEnd(classConstructor);
    classConstructor.addInstruction(new PopInstruction());

    methods.put("<clinit>", classConstructor);

    ByteCodeMethod init = new ByteCodeMethod(/* jumps */ false, voidType, "public", "<init>");
    init.addInstruction(new LocalLoadInstruction(objectType, 0));
    init.addInstruction(new NonVirtualCallInstruction(voidType, getSuperClassName() + "/<init>"));
    init.addInstruction(new LocalLoadInstruction(runtimeValueType, 0));
    init.addInstruction(new NonVirtualCallInstruction(voidType, ReflectiveEnvironment.class, "bind"));
    methods.put("<init>", init);
  }

  @Override
  public ByteCodeMethod getPrimaryMethod()
  { return getMainInnerClass().getPrimaryMethod();
  }

  @Override
  public String getSuperClassName()
  { return ReflectiveEnvironment.class.getName().replace('.', '/');
  }

  public void addLibraryExport(String name) // ToDo: rename
  {
  }
}
