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
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.lambdaValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.callValueType;
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
  { instructionList.add(instruction.byteCode());
    instruction.simulateLimits(this);
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

  public void checkCanJump()
  { if (! jump) throw new InternalCompilerException("Tried to add a jump to \"" + toString() + "\" without enabling jumps");
  }

  public String jumpPreamble()
  { if (! jump) return "";
    checkCanJump();
    return
      new StaticCallInstruction(integerType, CallStack.class, "getProgrammeCounter").byteCode() + "\n" +
      new TableSwitchInstruction("InvalidJump", 0, "Jump", jumpCounter).byteCode() + "\n" +
      invalidJump() + "\n" +
      "Jump0:";
  }

  // Don't care about stack values here as it ends with a throw
  public String invalidJump()
  { return
      "InvalidJump:\n" +
      new LocalLoadInstruction(runtimeValueType, 1).byteCode() + "\n" +
      new StaticCallInstruction(voidType, CallStack.class, "invalidJump", runtimeValueType).byteCode();
  }

  // One value on stack before, one value on stack after
  public void addJump()
  { checkCanJump();
    addInstruction(new LocalStoreInstruction(runtimeValueType, 1));
    int valueCount = storeValues();
    addInstruction(new LocalLoadInstruction(lambdaValueType, 0));
    addInstruction(new StaticCallInstruction(voidType, CallStack.class, "addFrame", lambdaValueType));
    addInstruction(new LocalLoadInstruction(runtimeValueType, 1));
    addInstruction(new ReturnInstruction(callValueType));
    jumpCounter++;
    addInstruction(new LabelPseudoInstruction("Jump" + jumpCounter));
    restoreValues(valueCount);
    addInstruction(new LocalLoadInstruction(runtimeValueType, 1));
  }

  public int storeValues()
  { int storedStackCount = stackCount;
    for (int i = 0; i < storedStackCount; i++)
    { addInstruction(new StaticCallInstruction(voidType, CallStack.class, "pushValue", runtimeValueType));
    }
    return storedStackCount;
  }

  public void restoreValues(int storedStackCount)
  { for (int i = 0; i < storedStackCount; i++)
    { addInstruction(new StaticCallInstruction(runtimeValueType, CallStack.class, "popValue"));
    }
  }
}
