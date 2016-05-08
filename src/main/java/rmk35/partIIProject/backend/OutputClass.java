package rmk35.partIIProject.backend;

import rmk35.partIIProject.Compiler;
import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.IdentifierValue;

import rmk35.partIIProject.backend.instructions.Instruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.JVMType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.objectType;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import jasmin.ClassFile;

public abstract class OutputClass
{ List<String> fullName;
  Map<String, String> fields;
  Map<String, ByteCodeMethod> methods;
  long uniqueNumber = 0;

  public OutputClass(String... name)
  { this(Arrays.asList(name));
  }
  public OutputClass(List<String> fullName)
  { this.fullName = fullName;
    fields = new HashMap<>();
    methods = new HashMap<>();

    ByteCodeMethod init = new ByteCodeMethod(/* jumps */ false, voidType, "public", "<init>");
    init.addInstruction(new LocalLoadInstruction(objectType, 0));
    init.addInstruction(new NonVirtualCallInstruction(voidType, getSuperClassName() + "/<init>"));

    methods.put("<init>", init);
    methods.put("<clinit>", new ByteCodeMethod(/* jumps */ false, voidType, "public static", "<clinit>"));
 }

  public List<String> getFullName()
  { return fullName;
  }

  public String getName()
  { return String.join("/", fullName);
  }

  public String getClassName()
  { return namePart(fullName);
  }

  public List<String> getPackage()
  { return packagePart(fullName);
  }

  public long uniqueNumber()
  { return uniqueNumber++;
  }

  public void ensureFieldExists(String modifier, String name, JVMType type)
  { fields.put(name, ".field " + modifier + " " + name + " " + type.toString());
  }

  public ByteCodeMethod getInitializer() // Constructor
  { return methods.get("<init>");
  }

  public ByteCodeMethod getClassInitializer() // Static block
  { return methods.get("<clinit>");
  }

  public String byteCode()
  { StringBuilder returnValue = new StringBuilder();
    returnValue.append(".class public ");
    returnValue.append(String.join("/", getFullName()));
    returnValue.append("\n");

    returnValue.append(".super ");
    returnValue.append(getSuperClassName());
    returnValue.append("\n\n");

    returnValue.append(String.join("\n", fields.values()));
    returnValue.append("\n\n");

    for (ByteCodeMethod method : methods.values())
    { returnValue.append(method.byteCode());
      returnValue.append("\n\n");
    }

    return returnValue.toString();
  }

  public byte[] assembledByteCode() throws Exception, IOException
  { ClassFile compiledClass= new ClassFile();
    Reader byteCodeReader = new StringReader(byteCode());
    ByteArrayOutputStream returnValue = new ByteArrayOutputStream();
    // Throws Exception....
    if (Compiler.intermediateCode)
    { compiledClass.readJasmin(byteCodeReader, getClassName() + ".j", /* numberLines */ true);
    } else
    { compiledClass.readJasmin(byteCodeReader, getClassName() + ".class", /* numberLines */ true);
    }
    if (compiledClass.errorCount() != 0) throw new InternalCompilerException("Something went wrong, but no idea what");
    byteCodeReader.close();
    compiledClass.write(returnValue);
    return returnValue.toByteArray();
  }

  public void saveToDisk() throws IOException
  { try (BufferedWriter writer =
            new BufferedWriter
              (new FileWriter(getClassName() + ".j")))
    { writer.append(byteCode());
    }
  }

  public void assembleToDisk() throws Exception, IOException
  { try (BufferedOutputStream outputStream =
            new BufferedOutputStream
              (new FileOutputStream(getClassName() + ".class")))
    { outputStream.write(assembledByteCode());
    }
  }

  /* Gets the primary method of the class, either the main or the apply method (for Main and Inner classes respectively) */
  public abstract ByteCodeMethod getPrimaryMethod();
  public abstract String getSuperClassName();

  public static String namePart(List<String> packageAndName)
  { return packageAndName.get(packageAndName.size()-1);
  }
  public static List<String> packagePart(List<String> packageAndName)
  { List<String> returnValue = new ArrayList<>();
    for (int i = 0; i < packageAndName.size()-1; i++)
    { returnValue.add(packageAndName.get(i));
    }
    return returnValue;
  }
}
