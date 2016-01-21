package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.IntegerConstantInstruction;
import rmk35.partIIProject.backend.instructions.NewReferenceArrayInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.ReferenceArrayStoreInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
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
{ Statement method;
  Statement thisObject;
  List<Statement> arguments;

  private static final ObjectType objectType = new ObjectType(Object.class);

  public JavaCallStatement(Statement method, Statement thisObject, List<Statement> arguments)
  { this.method = method;
    this.thisObject = thisObject;
    this.arguments = arguments;
  }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("JavaCallStatement"));
    method.generateOutput(output);
    output.addToPrimaryMethod(new CheckCastInstruction(Method.class));
    thisObject.generateOutput(output);

    // Make array for variadic arguments
    output.addToPrimaryMethod(new IntegerConstantInstruction(arguments.size()));
    output.addToPrimaryMethod(new NewReferenceArrayInstruction(Object.class));
    int i = 0;
    for (Statement argument : arguments)
    { output.addToPrimaryMethod(new DupInstruction()); // Invariant: Array on top of stack
      output.addToPrimaryMethod(new IntegerConstantInstruction(i));
      argument.generateOutput(output);
      output.addToPrimaryMethod(new ReferenceArrayStoreInstruction());
      i++;
    }
    output.addToPrimaryMethod(new VirtualCallInstruction(objectType, "java/lang/reflect/Method/invoke", objectType, new ArrayType(objectType)));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> returnValue = new TreeSet<>();
    returnValue.addAll(method.getFreeIdentifiers());
    returnValue.addAll(thisObject.getFreeIdentifiers());
    arguments.parallelStream()
             .map(statement -> statement.getFreeIdentifiers())
             .forEach(collection -> returnValue.addAll(collection));
    return returnValue;
  }
}