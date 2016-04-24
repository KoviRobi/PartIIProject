package rmk35.partIIProject.runtime;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.middle.ASTVisitor;

import java.util.List;
import java.util.ArrayList;

import lombok.Value;

public class CallStack
{ static boolean started = false;
  static List<CallFrame> callStack = new ArrayList<>();
  static List<RuntimeValue> valueStack = new ArrayList<>();
  static int programmeCounter = 0;

  public static void incrementProgrammeCounter()
  { programmeCounter++;
  }

  public static int getProgrammeCounter()
  { return programmeCounter;
  }

  public static void invalidJump(RuntimeValue value)
  { throw new RuntimeException("Invalid jump to " + programmeCounter + " with value \"" + value.toString() + "\" and stack \"" + valueStack.toString() + "\"");
  }

  public static void addFrame(LambdaValue function)
  { // Programme counter past current position
    callStack.add(new CallFrame(function, valueStack, programmeCounter+1));
    valueStack = new ArrayList<>();
    programmeCounter = 0;
  }

  public static void pushValue(RuntimeValue value) { valueStack.add(value); }
  public static RuntimeValue popValue() { return valueStack.remove(valueStack.size()-1); }

  public static RuntimeValue start(LambdaValue function, RuntimeValue arguments)
  { if (started) throw new InternalCompilerException("Already have one stack in use!");
    started = true;

    addFrame(function);
    RuntimeValue currentValue = arguments;
    while (! callStack.isEmpty())
    { CallFrame currentFrame = callStack.remove(callStack.size()-1);
      valueStack = currentFrame.getValueStack();
      programmeCounter = currentFrame.getProgrammeCounter();
      currentValue = currentFrame.continueWith(currentValue);
      // Tail calls
      while (currentValue instanceof CallValue)
      { valueStack = new ArrayList<>();
        programmeCounter = 0;
        currentValue = ((CallValue) currentValue).call();
      }
    }
    return currentValue;
  }
}

@Value
class CallFrame
{ LambdaValue function;
  List<RuntimeValue> valueStack;
  int programmeCounter;

  public CallFrame(LambdaValue function, List<RuntimeValue> valueStack, int programmeCounter)
  { this.function =  function;
    this.valueStack = valueStack;
    this.programmeCounter = programmeCounter;
  }

  public RuntimeValue continueWith(RuntimeValue value)
  { return function.run(value);
  }
}
