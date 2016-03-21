package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ValueHelper;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.InterfaceCallInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.IntegerConstantInstruction;
import rmk35.partIIProject.backend.instructions.NewReferenceArrayInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.ReferenceArrayStoreInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import rmk35.partIIProject.backend.instructions.types.JVMType;
import rmk35.partIIProject.backend.instructions.types.ObjectType;
import rmk35.partIIProject.backend.instructions.types.ArrayType;

import java.util.List;
import java.util.Collection;
import java.util.TreeSet;

import java.lang.reflect.Method;

import lombok.ToString;

@ToString
public class JavaCallStatement extends Statement
{ Statement methodToCall;
  Statement thisObject;
  List<Statement> arguments;

  private static final ObjectType objectType = new ObjectType(Object.class);
  private static final ObjectType runtimeValueType = new ObjectType(RuntimeValue.class);

  public JavaCallStatement(Statement methodToCall, Statement thisObject, List<Statement> arguments)
  { this.methodToCall = methodToCall;
    this.thisObject = thisObject;
    this.arguments = arguments;
  }

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("JavaCallStatement"));
    methodToCall.generateOutput(mainClass, outputClass, method);
    method.addInstruction(new InterfaceCallInstruction(false, objectType, RuntimeValue.class.getName().replace('.', '/') + "/toJavaValue"));
    method.addInstruction(new CheckCastInstruction(Method.class));
    thisObject.generateOutput(mainClass, outputClass, method);
    method.addInstruction(new InterfaceCallInstruction(false, objectType, RuntimeValue.class.getName().replace('.', '/') + "/toJavaValue"));

    // Make array for variadic arguments
    method.addInstruction(new IntegerConstantInstruction(arguments.size()));
    method.addInstruction(new NewReferenceArrayInstruction(Object.class));
    int i = 0;
    for (Statement argument : arguments)
    { method.addInstruction(new DupInstruction()); // Invariant: Array on top of stack
      method.addInstruction(new IntegerConstantInstruction(i));
      argument.generateOutput(mainClass, outputClass, method);
      method.addInstruction(new InterfaceCallInstruction(false, objectType, RuntimeValue.class.getName().replace('.', '/') + "/toJavaValue"));
      method.addInstruction(new ReferenceArrayStoreInstruction());
      i++;
    }
    method.addInstruction(new VirtualCallInstruction(objectType, Method.class.getName().replace('.', '/') + "/invoke", objectType, new ArrayType(objectType)));
    method.addInstruction(new StaticCallInstruction(runtimeValueType, ValueHelper.class.getName().replace('.', '/') + "/toSchemeValue", objectType));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> returnValue = new TreeSet<>();
    returnValue.addAll(methodToCall.getFreeIdentifiers());
    returnValue.addAll(thisObject.getFreeIdentifiers());
    arguments.parallelStream()
             .map(statement -> statement.getFreeIdentifiers())
             .forEach(collection -> returnValue.addAll(collection));
    return returnValue;
  }
}
