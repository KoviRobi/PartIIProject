package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.ConsValue;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.SwapInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;

import java.util.List;
import java.util.Arrays;
import java.util.ListIterator;

import lombok.ToString;

@ToString
public class ApplicationStatement extends Statement
{ Statement operator;
  List<Statement> operands;

  public ApplicationStatement(Statement operator, List<Statement> operands)
  { this.operator = operator;
    this.operands = operands;
  }

  public ApplicationStatement(Statement operator, Statement... operands)
  { this.operator = operator;
    this.operands = Arrays.asList(operands);
  }

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("ApplicationStatement"));
    Compiler.tailCallSettings.generateCallStart(method);

    operator.generateOutput(mainClass, outputClass, method);
    Compiler.tailCallSettings.generateContinuation(method);
    method.addInstruction(new CheckCastInstruction(LambdaValue.class));

    // Create a new list of operands
    method.addInstruction(new NewObjectInstruction(NullValue.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new NonVirtualCallInstruction(voidType, NullValue.class, "<init>"));
    ListIterator<Statement> iterator = operands.listIterator(operands.size());
    while (iterator.hasPrevious())
    { Statement operand = iterator.previous();
      operand.generateOutput(mainClass, outputClass, method);
      Compiler.tailCallSettings.generateContinuation(method);
      /* Swap car (currently top, from generateOutput) and cdr (below top, from invariant) */
      method.addInstruction(new SwapInstruction());
      method.addInstruction(new StaticCallInstruction(runtimeValueType, ConsValue.class, "create", runtimeValueType, runtimeValueType));
    }

    Compiler.tailCallSettings.generateCallEnd(method);
  }
}
