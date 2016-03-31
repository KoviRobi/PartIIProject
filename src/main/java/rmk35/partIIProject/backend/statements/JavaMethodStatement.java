package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.StringValue;
import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.TrampolineValue;
import rmk35.partIIProject.runtime.ObjectValue;
import rmk35.partIIProject.runtime.ValueHelper;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.IntegerConstantInstruction;
import rmk35.partIIProject.backend.instructions.NewReferenceArrayInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.ReferenceArrayStoreInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.objectValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.stringType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.stringArrayType;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class JavaMethodStatement extends Statement
{ Statement object;
  Statement methodName;
  List<Statement> arguments;

  public JavaMethodStatement(Statement object, Statement methodName, List<Statement> arguments)
  { this.object = object;
    this.methodName = methodName;
    this.arguments = arguments;
 }

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("JavaMethodStatement"));
    object.generateOutput(mainClass, outputClass, method);
    method.addInstruction(new StaticCallInstruction(runtimeValueType, TrampolineValue.class.getName().replace('.', '/') + "/bounceHelper", runtimeValueType));
    method.addInstruction(new CheckCastInstruction(ObjectValue.class));
    methodName.generateOutput(mainClass, outputClass, method);
    method.addInstruction(new StaticCallInstruction(runtimeValueType, TrampolineValue.class.getName().replace('.', '/') + "/bounceHelper", runtimeValueType));
    method.addInstruction(new CheckCastInstruction(StringValue.class));
    method.addInstruction(new VirtualCallInstruction(stringType, StringValue.class.getName().replace('.', '/') + "/getValue"));

    // Make array for variadic arguments
    method.addInstruction(new IntegerConstantInstruction(arguments.size()));
    method.addInstruction(new NewReferenceArrayInstruction(String.class));
    int i = 0;
    for (Statement argument : arguments)
    { method.addInstruction(new DupInstruction()); // Invariant: Array on top of stack
      method.addInstruction(new IntegerConstantInstruction(i));
      argument.generateOutput(mainClass, outputClass, method);
      method.addInstruction(new StaticCallInstruction(runtimeValueType, TrampolineValue.class.getName().replace('.', '/') + "/bounceHelper", runtimeValueType));
      method.addInstruction(new CheckCastInstruction(StringValue.class));
      method.addInstruction(new VirtualCallInstruction(stringType, StringValue.class.getName().replace('.', '/') + "/getValue"));
      method.addInstruction(new ReferenceArrayStoreInstruction());
      i++;
    }

    method.addInstruction(new StaticCallInstruction(runtimeValueType, JavaMethodStatement.class.getName().replace('.', '/') + "/getMethod", objectValueType, stringType, stringArrayType));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> returnValue = new TreeSet<>();
    returnValue.addAll(object.getFreeIdentifiers());
    returnValue.addAll(methodName.getFreeIdentifiers());
    arguments.parallelStream()
             .map(statement -> statement.getFreeIdentifiers())
             .forEach(collection -> returnValue.addAll(collection));
    return returnValue;
  }

  public static RuntimeValue getMethod(ObjectValue object, String methodName, String... argumentClassNames)
  { try
    { Class[] argumentTypes = new Class[argumentClassNames.length];
       for (int i = 0; i < argumentTypes.length; i++)
      { argumentTypes[i] = Class.forName(argumentClassNames[i]);
      }
      return ValueHelper.toSchemeValue(object.toJavaValue().getClass().getDeclaredMethod(methodName, argumentTypes));
    } catch (NoSuchMethodException | ClassNotFoundException exception)
    { throw new RuntimeException("Can't get method", exception);
    }
  }
}
