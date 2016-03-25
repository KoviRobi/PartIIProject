package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.TrampolineValue;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.DupX1Instruction;
import rmk35.partIIProject.backend.instructions.SwapInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import rmk35.partIIProject.backend.instructions.types.ObjectType;
import rmk35.partIIProject.backend.instructions.types.BooleanType;
import rmk35.partIIProject.backend.instructions.types.IntegerType;
import rmk35.partIIProject.backend.instructions.types.VoidType;

import java.util.List;
import java.util.ListIterator;
import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class ApplicationStatement extends Statement
{ Statement operator;
  List<Statement> operands;

  private static final VoidType voidType = new VoidType();
  private static final ObjectType runtimeType = new ObjectType(RuntimeValue.class);

  public ApplicationStatement(Statement operator, List<Statement> operands)
  { this.operator = operator;
    this.operands = operands;
  }

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("ApplicationStatement"));
    method.addInstruction(new NewObjectInstruction(TrampolineValue.class));
    method.addInstruction(new DupInstruction());

    operator.generateOutput(mainClass, outputClass, method);
    method.addInstruction(new StaticCallInstruction(new ObjectType(RuntimeValue.class), TrampolineValue.class.getName().replace('.', '/') + "/bounceHelper", new ObjectType(RuntimeValue.class)));
    method.addInstruction(new CheckCastInstruction(LambdaValue.class));

    // Create a new list of operands
    method.addInstruction(new NewObjectInstruction(NullValue.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new NonVirtualCallInstruction(voidType, NullValue.class.getName().replace('.', '/') + "/<init>"));
    ListIterator<Statement> iterator = operands.listIterator(operands.size());
    while (iterator.hasPrevious())
    { Statement operand = iterator.previous();
      method.addInstruction(new NewObjectInstruction(ConsValue.class));
      /* Duplicate to below null (or currently built list) so now Cons, Runtime, Cons */
      method.addInstruction(new DupX1Instruction());
      /* Swap with null (or currently built list) so now Cons, Cons, Runtime */
      method.addInstruction(new SwapInstruction());
      /* Generate output so now Cons, Cons, Runtime, Car */
      operand.generateOutput(mainClass, outputClass, method);
      method.addInstruction(new StaticCallInstruction(new ObjectType(RuntimeValue.class), TrampolineValue.class.getName().replace('.', '/') + "/bounceHelper", new ObjectType(RuntimeValue.class)));
      /* Swap with generated output so now Cons, Cons, Car, Runtime */
      method.addInstruction(new SwapInstruction());
      method.addInstruction(new NonVirtualCallInstruction(voidType, ConsValue.class.getName().replace('.', '/') + "/<init>", runtimeType, runtimeType));
    }

    // Wrap call in a TrampolineVisitor
    method.addInstruction(new NonVirtualCallInstruction(voidType, TrampolineValue.class.getName().replace('.', '/') + "/<init>", new ObjectType(LambdaValue.class), runtimeType));
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
