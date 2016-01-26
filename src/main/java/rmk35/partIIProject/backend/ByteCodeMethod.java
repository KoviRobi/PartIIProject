package rmk35.partIIProject.backend;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.backend.instructions.Instruction;
import rmk35.partIIProject.backend.instructions.BeginMethodDirective;
import rmk35.partIIProject.backend.instructions.ReturnInstruction;
import rmk35.partIIProject.backend.instructions.EndMethodDirective;
import rmk35.partIIProject.backend.instructions.types.JVMType;

import java.util.List;
import java.util.ArrayList;

public class ByteCodeMethod
{ JVMType returnType;
  String methodName;
  JVMType[] arguments;

  int stackCount, stackLimit;
  int localLimit;

  List<String> header;
  List<String> instructions;
  List<String> footer;

  String cachedByteCode = null; // As byteCode() mutates state

  public ByteCodeMethod(JVMType returnType, String modifier, String methodName, JVMType... arguments)
  { this.returnType = returnType;
    this.methodName = methodName;
    this.arguments = arguments;

    header = new ArrayList<>();
    addInstruction(new BeginMethodDirective(returnType, modifier, methodName, arguments), header);
    instructions = new ArrayList<>();
    footer = new ArrayList<>();

    stackCount = stackLimit = 0;
    localLimit =
      modifier.contains("static")
        ? arguments.length
        : (arguments.length + 1);
  }

  private void addInstruction(Instruction instruction, List<String> instructionList)
  { if (cachedByteCode != null) throw new InternalCompilerException("Adding instruction after calling byteCode");
    instruction.simulateLimits(this);
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
  
  public void ensureLocal(int i)
  { localLimit = Math.max(localLimit, i+1); // +1 because of local 0
  }

  public void setStackCount(int i)
  { stackCount = i;
    stackLimit = Math.max(stackLimit, stackCount);
  }

  public String byteCode()
  { addInstruction(new ReturnInstruction(returnType), footer); // Mutates state (limits)
    addInstruction(new EndMethodDirective(), footer);
    cachedByteCode = String.join("\n", header) + "\n" +
      "  .limit stack " + stackLimit + "\n" +
      "  .limit locals " + localLimit + "\n" +
      String.join("\n", instructions) + "\n" +
      String.join("\n", footer);
    return cachedByteCode;
  }
}