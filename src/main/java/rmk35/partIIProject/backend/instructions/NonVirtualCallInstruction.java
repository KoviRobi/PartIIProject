package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.types.JVMType;

public class NonVirtualCallInstruction implements Instruction
{ String functionName;
  JVMType returnType;
  JVMType[] arguments;

  public NonVirtualCallInstruction(JVMType returnType, String functionName, JVMType... arguments)
  { this.returnType = returnType;
    this.functionName = functionName;
    this.arguments = arguments;
  }

  public NonVirtualCallInstruction(JVMType returnType, Class<?> className, String functionName, JVMType... arguments)
  { this.returnType = returnType;
    this.functionName = className.getName().replace('.', '/') + "/" +functionName;
    this.arguments = arguments;
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  { method.decrementStackCount(arguments.length + 1 - returnType.stackCount());
  }

  public String byteCode()
  { StringBuilder returnValue = new StringBuilder("  invokenonvirtual ");
    returnValue.append(functionName);
    returnValue.append("(");
    for (JVMType t : arguments)
    { returnValue.append(t);
    }
    returnValue.append(")");
    returnValue.append(returnType);
    return returnValue.toString();
  }
}
