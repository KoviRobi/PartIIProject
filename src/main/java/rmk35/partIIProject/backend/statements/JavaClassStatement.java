package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.StringValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
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

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("JavaClassStatement"));
    className.generateOutput(output);
    output.addToPrimaryMethod(new CheckCastInstruction(StringValue.class));
    output.addToPrimaryMethod(new VirtualCallInstruction(stringType, "java/lang/Object/toString"));
    output.addToPrimaryMethod(new NonVirtualCallInstruction(classType, "java/lang/Class/forName", stringType));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return className.getFreeIdentifiers();
  }
}