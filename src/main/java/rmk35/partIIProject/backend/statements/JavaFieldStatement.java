package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.StringValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import rmk35.partIIProject.backend.instructions.types.ObjectType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class JavaFieldStatement extends Statement
{ Statement object;
  Statement fieldName;

  private static final ObjectType stringType = new ObjectType(String.class);
  private static final ObjectType objectType = new ObjectType(Object.class);

  public JavaFieldStatement(Statement object, Statement fieldName)
  { this.object = object;
    this.fieldName = fieldName;
  }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("JavaFieldStatement"));
    object.generateOutput(output);
    fieldName.generateOutput(output);
    output.addToPrimaryMethod(new CheckCastInstruction(StringValue.class));
    output.addToPrimaryMethod(new VirtualCallInstruction(stringType, "java/lang/Object/toString"));
    output.addToPrimaryMethod(new StaticCallInstruction(objectType, "rmk35/partIIProject/backend/runtimeValues/IntrospectionHelper/getField", objectType, stringType));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> returnValue = new TreeSet<>();
    returnValue.addAll(object.getFreeIdentifiers());
    returnValue.addAll(fieldName.getFreeIdentifiers());
    return returnValue;
  }
}