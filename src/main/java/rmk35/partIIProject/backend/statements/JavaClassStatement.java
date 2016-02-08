package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.runtime.StringValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import rmk35.partIIProject.backend.instructions.types.ObjectType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class JavaClassStatement extends Statement
{ Statement className;

  private static final ObjectType stringType = new ObjectType(String.class);
  private static final ObjectType classType = new ObjectType(Class.class);

  public JavaClassStatement(Statement className)
  { this.className = className;
  }

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("JavaClassStatement"));
    className.generateOutput(mainClass, outputClass, method);
    method.addInstruction(new CheckCastInstruction(StringValue.class));
    method.addInstruction(new VirtualCallInstruction(stringType, "java/lang/Object/toString"));
    method.addInstruction(new StaticCallInstruction(classType, "java/lang/Class/forName", stringType));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return className.getFreeIdentifiers();
  }
}
