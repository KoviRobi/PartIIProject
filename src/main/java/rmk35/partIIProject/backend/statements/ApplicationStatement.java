package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
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

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class ApplicationStatement extends Statement
{ Statement operator;
  List<Statement> operands;

  public ApplicationStatement(Statement operator, List<Statement> operands)
  { this.operator = operator;
    this.operands = operands;
  }

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("ApplicationStatement"));
    operator.generateOutput(mainClass, outputClass, method);
    method.addInstruction(new CheckCastInstruction(LambdaValue.class));

    // Create new ArrayList for operands
    method.addInstruction(new NewObjectInstruction(ArrayList.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new IntegerConstantInstruction(operands.size()));
    method.addInstruction(new NonVirtualCallInstruction(new VoidType(), "java/util/ArrayList/<init>", new IntegerType()));

    for (Statement operand : operands)
    { method.addInstruction(new DupInstruction()); // Loop invariant is the list on the top of the stack
      operand.generateOutput(mainClass, outputClass, method);
      method.addInstruction(new InterfaceCallInstruction(/* static */ false, new BooleanType(), "java/util/List/add", new ObjectType(Object.class)));
      method.addInstruction(new PopInstruction()); // Pop returned boolean
    }

    // Invoke operator.run with argument of operand
    method.addInstruction(new VirtualCallInstruction(new ObjectType(RuntimeValue.class), "rmk35/partIIProject/runtime/LambdaValue/run", new ObjectType(List.class)));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> returnValue = new TreeSet<>();
    returnValue.addAll(operator.getFreeIdentifiers());
    operands.parallelStream()
               .map(statement -> statement.getFreeIdentifiers())
               .forEach(collection -> returnValue.addAll(collection));
    return returnValue;
  }
}
