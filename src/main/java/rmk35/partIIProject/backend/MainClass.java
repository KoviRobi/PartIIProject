package rmk35.partIIProject.backend;

import rmk35.partIIProject.backend.instructions.Instruction;
import rmk35.partIIProject.backend.instructions.types.JVMType;
import rmk35.partIIProject.backend.instructions.types.VoidType;
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

  public MainClass(String name)
  { this(name, new ArrayList<>());
  }
  public MainClass(String name, List<InnerClass> innerClasses)
  { super(name);
    this.innerClasses = innerClasses;
    ByteCodeMethod mainMethod = new ByteCodeMethod(new VoidType(), "public static", "main", new ArrayType(new ObjectType(String.class)));
    methods.put("main", mainMethod);
  }

  public void addInnerClass(InnerClass innerClass)
  { innerClasses.add(innerClass);
  }

  @Override
  public ByteCodeMethod getPrimaryMethod()
  { return methods.get("main");
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
}
