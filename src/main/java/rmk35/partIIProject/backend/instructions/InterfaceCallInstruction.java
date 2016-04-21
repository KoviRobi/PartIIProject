package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.types.JVMType;

public class InterfaceCallInstruction implements Instruction
{ String functionName;
  boolean staticCall;
  JVMType returnType;
  JVMType[] arguments;

  public InterfaceCallInstruction(boolean staticCall, JVMType returnType, String functionName, JVMType... arguments)
  { this.returnType = returnType;
    this.staticCall = staticCall;
    this.functionName = functionName;
    this.arguments = arguments;
  }

  private int inputArgumentCount()
  { return arguments.length + (staticCall? 0 : 1);
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  { method.decrementStackCount(inputArgumentCount() - returnType.stackCount());
  }

  public String byteCode()
  { StringBuilder returnValue = new StringBuilder("  invokeinterface ");
    returnValue.append(functionName);
    returnValue.append("(");
    for (JVMType t : arguments)
    { returnValue.append(t);
    }
    returnValue.append(")");
    returnValue.append(returnType);
    returnValue.append(" ");
    returnValue.append(inputArgumentCount());
    return returnValue.toString();
  }
}
