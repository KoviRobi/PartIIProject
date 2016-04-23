package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.types.JVMType;

public class BeginMethodDirective implements Instruction
{ String methodName;
  String modifier;
  JVMType returnType;
  JVMType[] arguments;

  public BeginMethodDirective(JVMType returnType, String modifier, String methodName, JVMType... arguments)
  { this.returnType = returnType;
    this.modifier = modifier;
    this.methodName = methodName;
    this.arguments = arguments;
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  {
  }

  public String byteCode()
  { StringBuilder returnValue = new StringBuilder(".method ");
    returnValue.append(modifier);
    returnValue.append(" ");
    returnValue.append(methodName);
    returnValue.append("(");
    for (JVMType t : arguments)
    { returnValue.append(t);
    }
    returnValue.append(")");
    returnValue.append(returnType);
    return returnValue.toString();
  }
}
