package rmk35.partIIProject.backend;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.CallStack;

import rmk35.partIIProject.backend.instructions.Instruction;
import rmk35.partIIProject.backend.instructions.BeginMethodDirective;
import rmk35.partIIProject.backend.instructions.ReturnInstruction;
import rmk35.partIIProject.backend.instructions.EndMethodDirective;
import rmk35.partIIProject.backend.instructions.TableSwitchInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.StringConstantInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
import rmk35.partIIProject.backend.instructions.ThrowInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import rmk35.partIIProject.backend.instructions.LocalStoreInstruction;
import rmk35.partIIProject.backend.instructions.LabelPseudoInstruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
import rmk35.partIIProject.backend.instructions.types.JVMType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.integerType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.stringType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.stringBuilderType;

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

  boolean jump;
  int jumpCounter;

  List<String> instructions;

  public ByteCodeMethod(boolean jump, JVMType returnType, String modifier, String methodName, JVMType... arguments)
  { this.modifier = modifier;
    this.returnType = returnType;
    this.methodName = methodName;
    this.arguments = arguments;

    instructions = new ArrayList<>();

    stackCount = 0;
    stackLimit = jump ? 4 : 0; // Because of invalidJump
    localLimit =
      modifier.contains("static")
        ? arguments.length
        : (arguments.length + 1);

    this.jump = jump;
    jumpCounter = 0;
  }

  private void addInstruction(Instruction instruction, List<String> instructionList)
  { instruction.simulateLimits(this);
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
    if (stackCount < 0) throw new InternalCompilerException("Simulated stack underflown for \"" + toString() + "\" with instructions " + instructions);
  }

  private void checkReturnStackCount()
  { if (stackCount != returnType.stackCount()) throw new InternalCompilerException("Return stack wrong for \"" + toString() + "\", got " + stackCount + " instead of " + returnType.stackCount() + " with instructions: " + instructions);
  }

  public String toString()
  { return returnType + " " + methodName + "(" + Arrays.toString(arguments) + ")";
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
      jumpPreamble() + "\n" +
      String.join("\n", instructions) + "\n" +
      new ReturnInstruction(returnType).byteCode() + "\n" +
      new EndMethodDirective().byteCode();
  }

  public void checkArgumentsForJump()
  { if (! jump) throw new InternalCompilerException("Tried to add a jump to \"" + toString() + "\" without enabling jumps");
    if (! (arguments.length == 3 &&
          (! modifier.contains("static")) &&
          arguments[1].equals(runtimeValueType) &&
          arguments[2].equals(integerType)))
      throw new InternalCompilerException("Jump used with wrong method types for \"" + toString() + "\"");
  }

  public String jumpPreamble()
  { if (! jump) return "";
    checkArgumentsForJump();
    return
      new LocalLoadInstruction(integerType, 3).byteCode() + "\n" +
      new TableSwitchInstruction("InvalidJump", 0, "Jump", jumpCounter).byteCode() + "\n" +
      invalidJump() + "\n" +
      "Jump0:";
  }

  // Don't care about stack values here as it ends with a throw
  public String invalidJump()
  { return
      "InvalidJump:\n" +
      new NewObjectInstruction(RuntimeException.class).byteCode() + "\n" +
      new DupInstruction().byteCode() + "\n" +
      new NewObjectInstruction(StringBuilder.class).byteCode() + "\n" +
      new DupInstruction().byteCode() + "\n" +
      new NonVirtualCallInstruction(voidType, StringBuilder.class, "<init>").byteCode() + "\n" +
      new StringConstantInstruction("Invalid jump to: ").byteCode() + "\n" +
      new VirtualCallInstruction(stringBuilderType, StringBuilder.class, "append", stringType).byteCode() + "\n" +
      new LocalLoadInstruction(integerType, 3).byteCode() + "\n" +
      new VirtualCallInstruction(stringBuilderType, StringBuilder.class, "append", integerType).byteCode() + "\n" +
      new StringConstantInstruction(" with continuation value: ").byteCode() + "\n" +
      new VirtualCallInstruction(stringBuilderType, StringBuilder.class, "append", stringType).byteCode() + "\n" +
      new LocalLoadInstruction(runtimeValueType, 2).byteCode() + "\n" +
      new VirtualCallInstruction(stringType, RuntimeValue.class, "toString").byteCode() + "\n" +
      new VirtualCallInstruction(stringBuilderType, StringBuilder.class, "append", stringType).byteCode() + "\n" +
      new VirtualCallInstruction(stringType, StringBuilder.class, "toString").byteCode() + "\n" +
      new NonVirtualCallInstruction(voidType, RuntimeException.class, "<init>", stringType).byteCode() + "\n" +
      new ThrowInstruction().byteCode();
  }

  // One value on stack before, one value on stack after
  public void addJump()
  { checkArgumentsForJump();
    addInstruction(new StaticCallInstruction(voidType, CallStack.class, "incrementProgrammeCounter")); // Increment programme counter
    addInstruction(new LocalStoreInstruction(runtimeValueType, 2)); // Store value (if we didn't jump to the label below, so that load restores value on top of stack)
    jumpCounter++;
    addInstruction(new LabelPseudoInstruction("Jump" + jumpCounter)); // Add label
    // Load continuation value
    addInstruction(new LocalLoadInstruction(runtimeValueType, 2));
  }
}
