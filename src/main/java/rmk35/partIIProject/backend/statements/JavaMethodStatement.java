package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.StringValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import rmk35.partIIProject.backend.instructions.types.ObjectType;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class JavaMethodStatement extends Statement
{ Statement object;
  Statement methodName;
  Statement[] arguments;

  private ObjectType[] argumentTypes;

  private static final ObjectType stringType = new ObjectType(String.class);
  private static final ObjectType objectType = new ObjectType(Object.class);

  public JavaMethodStatement(Statement object, Statement methodName, Statement... arguments)
  { this.object = object;
    this.methodName = methodName;
    this.arguments = arguments;
    argumentTypes = new ObjectType[arguments.length + 2]; // Object, method and method arguments
    Arrays.fill(argumentTypes, stringType);
    argumentTypes[0] = objectType;
  }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("JavaMethodStatement"));
    object.generateOutput(output);
    methodName.generateOutput(output);
    output.addToPrimaryMethod(new CheckCastInstruction(StringValue.class));
    output.addToPrimaryMethod(new VirtualCallInstruction(stringType, "java/lang/Object/toString"));
    for (Statement argument : arguments)
    { argument.generateOutput(output);
      output.addToPrimaryMethod(new CheckCastInstruction(StringValue.class));
      output.addToPrimaryMethod(new VirtualCallInstruction(stringType, "java/lang/Object/toString"));
    }
    output.addToPrimaryMethod(new StaticCallInstruction(objectType, "rmk35/partIIProject/backend/runtimeValues/IntrospectionHelper/getMethod", argumentTypes));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> returnValue = new TreeSet<>();
    returnValue.addAll(object.getFreeIdentifiers());
    returnValue.addAll(methodName.getFreeIdentifiers());
    Arrays.asList(arguments).parallelStream()
                              .map(statement -> statement.getFreeIdentifiers())
                              .forEach(collection -> returnValue.addAll(collection));
    return returnValue;
  }
}