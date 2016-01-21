package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.StringValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.IntegerConstantInstruction;
import rmk35.partIIProject.backend.instructions.NewReferenceArrayInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.ReferenceArrayStoreInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import rmk35.partIIProject.backend.instructions.types.JVMType;
import rmk35.partIIProject.backend.instructions.types.ObjectType;
import rmk35.partIIProject.backend.instructions.types.ArrayType;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class JavaMethodStatement extends Statement
{ Statement object;
  Statement methodName;
  Statement[] arguments;

  private static final ObjectType objectType = new ObjectType(Object.class);
  private static final ObjectType stringType = new ObjectType(String.class);
  private static final ObjectType methodType = new ObjectType(Method.class);

  public JavaMethodStatement(Statement object, Statement methodName, Statement... arguments)
  { this.object = object;
    this.methodName = methodName;
    this.arguments = arguments;
 }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("JavaMethodStatement"));
    object.generateOutput(output);
    methodName.generateOutput(output);
    output.addToPrimaryMethod(new CheckCastInstruction(StringValue.class));
    output.addToPrimaryMethod(new VirtualCallInstruction(stringType, "java/lang/Object/toString"));

    // Make array for variadic arguments
    output.addToPrimaryMethod(new IntegerConstantInstruction(arguments.length));
    output.addToPrimaryMethod(new NewReferenceArrayInstruction(String.class));
    for (int i = 0; i < arguments.length; i++)
    { output.addToPrimaryMethod(new DupInstruction()); // Invariant: Array on top of stack
      output.addToPrimaryMethod(new IntegerConstantInstruction(i));
      arguments[i].generateOutput(output);
      output.addToPrimaryMethod(new CheckCastInstruction(StringValue.class));
      output.addToPrimaryMethod(new VirtualCallInstruction(stringType, "java/lang/Object/toString"));
      output.addToPrimaryMethod(new ReferenceArrayStoreInstruction());
    }

    output.addToPrimaryMethod(new StaticCallInstruction(methodType, "rmk35/partIIProject/backend/runtimeValues/IntrospectionHelper/getMethod", objectType, stringType, new ArrayType(stringType)));
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