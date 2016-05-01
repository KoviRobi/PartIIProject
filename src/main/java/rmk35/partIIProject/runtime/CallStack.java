package rmk35.partIIProject.runtime;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.utility.Pair;
import rmk35.partIIProject.utility.FunctionalList;
import rmk35.partIIProject.utility.FunctionalListNull;
import rmk35.partIIProject.utility.FunctionalListCons;

import rmk35.partIIProject.middle.ASTVisitor;

import java.util.List;
import java.util.ArrayList;

import lombok.Value;

public class CallStack
{ static boolean started = false;
  static FunctionalList<CallFrame> callStack = new FunctionalListNull<>();
  static FunctionalList<RuntimeValue> valueStack = new FunctionalListNull<>();
  static FunctionalList<FunctionalList<CallFrame>> handlers = new FunctionalListNull<>();
  static FunctionalList<Pair<LambdaValue, LambdaValue>> dynamicPoint = new FunctionalListNull<>();
  static int programmeCounter = 0;

  public static void incrementProgrammeCounter()
  { programmeCounter++;
  }

  public static int getProgrammeCounter()
  { return programmeCounter;
  }

  public static void setProgrammeCounter(int newProgrammeCounter)
  { programmeCounter = newProgrammeCounter;
  }

  public static void invalidJump(RuntimeValue value)
  { throw new RuntimeException("Invalid jump to " + programmeCounter + " with value \"" + value.toString() + "\" and stack \"" + valueStack.toString() + "\"");
  }

  public static void addFrame(LambdaValue function)
  { // Programme counter past current position
    callStack= new FunctionalListCons<>(new CallFrame(function, valueStack, programmeCounter+1), callStack);
    valueStack = new FunctionalListNull<>();
    programmeCounter = 0;
  }

  public static ContinuationValue getContinuation()
  { return new ContinuationValue(callStack, valueStack, programmeCounter);
  }

  public static void setContinuation(ContinuationValue continuation)
  { callStack = continuation.getCallStack();
    valueStack = continuation.getValueStack();
    programmeCounter = continuation.getProgrammeCounter();
  }

  public static void addHandler(LambdaValue handler)
  { if (programmeCounter != 0 || ! valueStack.isEmpty()) throw new InternalCompilerException("Was expecting handler at start");
    handlers = new FunctionalListCons<>(new FunctionalListCons<>(new CallFrame(handler, new FunctionalListNull<>(), 0), callStack), handlers);
  }

  public static boolean restoreHandler()
  { if (handlers.isEmpty()) return false;
    callStack = handlers.head();
    handlers = handlers.tail();
    return true;
  }

//  public static List<Pair<LambdaValue, LambdaValue>> getDynamicPoints()
//  { return (ArrayList<Pair<LambdaValue, LambdaValue>>) ((ArrayList<Pair<LambdaValue, LambdaValue>>) dynamicPoint).clone();
//  }
//
//  public static void addDynamicPoint(LambdaValue before, LambdaValue after)
//  { dynamicPoint.add(new Pair<>(before, after));
//  }
//
//  public static void setDynamicPoint(List<Pair<LambdaValue, LambdaValue>> newDynamicPoint)
//  {// NEXT:
//  }

  public static void pushValue(RuntimeValue value) { valueStack = new FunctionalListCons<>(value, valueStack); }
  public static RuntimeValue popValue()
  { RuntimeValue returnValue = valueStack.head();
    valueStack = valueStack.tail();
    return returnValue;
  }

  public static RuntimeValue start(LambdaValue function, RuntimeValue arguments)
  { if (started) throw new InternalCompilerException("Already have one stack in use!");
    started = true;

    addFrame(function);
    RuntimeValue currentValue = arguments;
    while (! callStack.isEmpty())
    { CallFrame currentFrame = callStack.head();
      valueStack = currentFrame.getValueStack();
      programmeCounter = currentFrame.getProgrammeCounter();
      callStack = callStack.tail();
      currentValue = currentFrame.continueWith(currentValue);
      // Tail calls
      while (currentValue instanceof CallValue)
      { valueStack = new FunctionalListNull<>();
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
  FunctionalList<RuntimeValue> valueStack;
  int programmeCounter;

  public CallFrame(LambdaValue function, FunctionalList<RuntimeValue> valueStack, int programmeCounter)
  { this.function =  function;
    this.valueStack = valueStack;
    this.programmeCounter = programmeCounter;
  }

  public RuntimeValue continueWith(RuntimeValue value)
  { return function.run(value);
  }
}
