package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.ObjectType;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

import java.lang.reflect.Method;

import lombok.ToString;

@ToString
public class JavaCallStatement extends Statement
{ Statement method;
  Statement thisObject;
  Statement[] arguments;

  private ObjectType[] argumentTypes;

  private static final ObjectType objectType = new ObjectType(Object.class);

  public JavaCallStatement(Statement method, Statement thisObject, Statement... arguments)
  { this.method = method;
    this.thisObject = thisObject;
    this.arguments = arguments;
    argumentTypes = new ObjectType[arguments.length];
    Arrays.fill(argumentTypes, objectType);
  }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("JavaCallStatement"));
    method.generateOutput(output);
    output.addToPrimaryMethod(new CheckCastInstruction(Method.class));
    thisObject.generateOutput(output);
    for (Statement argument : arguments)
    { argument.generateOutput(output);
    }
    output.addToPrimaryMethod(new VirtualCallInstruction(objectType, "java/lang/reflect/Method/invoke", argumentTypes));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> returnValue = new TreeSet<>();
    returnValue.addAll(method.getFreeIdentifiers());
    returnValue.addAll(thisObject.getFreeIdentifiers());
    Arrays.asList(arguments).parallelStream()
                              .map(statement -> statement.getFreeIdentifiers())
                              .forEach(collection -> returnValue.addAll(collection));
    return returnValue;
  }
}