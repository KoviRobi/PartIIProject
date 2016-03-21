package rmk35.partIIProject.backend;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.backend.instructions.Instruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.JVMType;
import rmk35.partIIProject.backend.instructions.types.VoidType;
import rmk35.partIIProject.backend.instructions.types.ObjectType;

import java.util.Map;
import java.util.Hashtable;
import java.util.Set;
import java.util.HashSet;
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

import jasmin.ClassFile;

public abstract class OutputClass
{ String name;
  Set<String> fields;
  Map<String, ByteCodeMethod> methods;
  int uniqueNumber = 0;

  private static final JVMType voidType = new VoidType();

  public OutputClass(String name)
  { this.name = name;
    fields = new HashSet<>();
    methods = new Hashtable<>();

    ByteCodeMethod init = new ByteCodeMethod(voidType, "public", "<init>");
    init.addInstruction(new LocalLoadInstruction(new ObjectType(), 0));
    init.addInstruction(new NonVirtualCallInstruction(voidType, getSuperClassName() + "/<init>"));

    methods.put("<init>", init);
    methods.put("<clinit>", new ByteCodeMethod(voidType, "public static", "<clinit>"));
 }

  public String getName()
  { return name;
  }

  public String uniqueID()
  { uniqueNumber++;
    return getName() + Integer.toString(uniqueNumber);
  }

  public void ensureFieldExists(String modifier, String name, JVMType type)
  { fields.add(".field " + modifier + " " + name + " " + type.toString());
  }

  public ByteCodeMethod getInitializer() // Constructor
  { return methods.get("<init>");
  }

  public ByteCodeMethod getClassInitializer() // Static block
  { return methods.get("<clinit>");
  }

  public String byteCode()
  { StringBuilder returnValue = new StringBuilder();
    returnValue.append(".class ");
    returnValue.append(getName());
    returnValue.append("\n");

    returnValue.append(".super ");
    returnValue.append(getSuperClassName());
    returnValue.append("\n\n");

    returnValue.append(String.join("\n", fields));
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
    compiledClass.readJasmin(byteCodeReader, getName() + ".class", /* numberLines */ true);
    if (compiledClass.errorCount() != 0) throw new InternalCompilerException("Something went wrong, but no idea what");
    byteCodeReader.close();
    compiledClass.write(returnValue);
    return returnValue.toByteArray();
  }

  public void saveToDisk() throws IOException
  { try (BufferedWriter writer =
            new BufferedWriter
              (new FileWriter(getName() + ".j")))
    { writer.append(byteCode());
    }
  }

  public void assembleToDisk() throws Exception, IOException
  { try (BufferedOutputStream outputStream =
            new BufferedOutputStream
              (new FileOutputStream(getName() + ".class")))
    { outputStream.write(assembledByteCode());
    }
  }

  /* Gets the primary method of the class, either the main or the apply method (for Main and Inner classes respectively) */
  public abstract ByteCodeMethod getPrimaryMethod();
  /** Generates a unique ID that does not start with a number */
  public abstract String getSuperClassName();
}
