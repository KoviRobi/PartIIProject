package rmk35.partIIProject.backend;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.backend.instructions.Instruction;
import rmk35.partIIProject.backend.instructions.BeginMethodDirective;
import rmk35.partIIProject.backend.instructions.ReturnInstruction;
import rmk35.partIIProject.backend.instructions.EndMethodDirective;
import rmk35.partIIProject.backend.instructions.types.JVMType;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ByteCodeMethod
{ String modifier; // Access modifier, e.g. private
  JVMType returnType;
  String methodName;
  JVMType[] arguments;

  int stackCount, stackLimit;
  int localLimit;

  List<String> instructions;

  public ByteCodeMethod(JVMType returnType, String modifier, String methodName, JVMType... arguments)
  { this.modifier = modifier;
    this.returnType = returnType;
    this.methodName = methodName;
    this.arguments = arguments;

    instructions = new ArrayList<>();

    stackCount = stackLimit = 0;
    localLimit =
      modifier.contains("static")
        ? arguments.length
        : (arguments.length + 1);
  }

  private void addInstruction(Instruction instruction, List<String> instructionList)
  {instruction.simulateLimits(this);
    instructionList.add(instruction.byteCode());
  }
  public void addInstruction(Instruction instruction)
  { addInstruction(instruction, instructions);
  }

  public void incrementStackCount(int i)
  { stackCount += i;
    stackLimit = Math.max(stackLimit, stackCount);
  }

  public void decrementStackCount(int i)
  { stackCount -= i;
    if (stackCount < 0) throw new InternalCompilerException("Simulated stack underflown");
  }

  private void checkReturnStackCount()
  { if (stackCount != returnType.stackCount()) throw new InternalCompilerException("Return stack wrong for \"" + returnType + " " + methodName + "(" + Arrays.toString(arguments) + ")\", got " + stackCount + " instead of " + returnType.stackCount() + " with instructions: " + instructions);
  }

  public void ensureLocal(int i)
  { localLimit = Math.max(localLimit, i+1); // +1 because of local 0
  }

  public void setStackCount(int i)
  { stackCount = i;
    stackLimit = Math.max(stackLimit, stackCount);
  }

  public String byteCode()
  { checkReturnStackCount();
    return new BeginMethodDirective(returnType, modifier, methodName, arguments).byteCode() + "\n" +
      "  .limit stack " + stackLimit + "\n" +
      "  .limit locals " + localLimit + "\n" +
      String.join("\n", instructions) + "\n" +
      new ReturnInstruction(returnType).byteCode() + "\n" +
      new EndMethodDirective().byteCode();
  }
}