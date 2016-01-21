package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.RuntimeValue;
import rmk35.partIIProject.backend.runtimeValues.LambdaValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.IntegerConstantInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.InterfaceCallInstruction;
import rmk35.partIIProject.backend.instructions.PopInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.ObjectType;
import rmk35.partIIProject.backend.instructions.types.BooleanType;
import rmk35.partIIProject.backend.instructions.types.IntegerType;
import rmk35.partIIProject.backend.instructions.types.VoidType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class ApplicationStatement extends Statement
{ Statement[] application;

  public ApplicationStatement(Statement... application)
  { this.application =  application;
  }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("ApplicationStatement"));
    application[0].generateOutput(output);
    output.addToPrimaryMethod(new CheckCastInstruction(LambdaValue.class));
    
    // Create new ArrayList for operands
    output.addToPrimaryMethod(new NewObjectInstruction(ArrayList.class));
    output.addToPrimaryMethod(new DupInstruction());
    output.addToPrimaryMethod(new IntegerConstantInstruction(application.length));
    output.addToPrimaryMethod(new NonVirtualCallInstruction(new VoidType(), "java/util/ArrayList/<init>", new IntegerType()));

    for (int i = 1; i < application.length; i++)
    { output.addToPrimaryMethod(new DupInstruction()); // Loop invariant is the list on the top of the stack
      application[i].generateOutput(output);
      output.addToPrimaryMethod(new InterfaceCallInstruction(/* static */ false, new BooleanType(), "java/util/List/add", new ObjectType(Object.class)));
      output.addToPrimaryMethod(new PopInstruction());
    }

    // Invoke operator.run with argument of operand
    output.addToPrimaryMethod(new VirtualCallInstruction(new ObjectType(RuntimeValue.class), "rmk35/partIIProject/backend/runtimeValues/LambdaValue/run", new ObjectType(ArrayList.class)));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> returnValue = new TreeSet<>();
    Arrays.asList(application).parallelStream()
                              .map(statement -> statement.getFreeIdentifiers())
                              .forEach(collection -> returnValue.addAll(collection));
    return returnValue;
  }
}
