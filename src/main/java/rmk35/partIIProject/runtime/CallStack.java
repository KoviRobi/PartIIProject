package rmk35.partIIProject.runtime;

import rmk35.partIIProject.Compiler;
import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.utility.Pair;
import rmk35.partIIProject.utility.FunctionalList;
import rmk35.partIIProject.utility.FunctionalListNull;
import rmk35.partIIProject.utility.FunctionalListCons;

import rmk35.partIIProject.middle.ASTVisitor;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import lombok.Value;

public class CallStack
{ FunctionalList<CallFrame> callStack = new FunctionalListNull<>();
  FunctionalList<RuntimeValue> valueStack = new FunctionalListNull<>();
  FunctionalList<Pair<FunctionalList<CallFrame>, LambdaValue>> handlers = new FunctionalListNull<>();
  FunctionalList<Pair<LambdaValue, LambdaValue>> dynamicPoint = new FunctionalListNull<>();
  int programmeCounter = 0;

  Map<String, Long> profile = new HashMap<>();
  long sumTime = 0;
  long startTime = 0;
  long endTime = 0;

  static CallStack currentCallStack; // ToDo: Make this per thread, e.g using a thread ID to CallStack map

  public static CallStack getCurrentCallStack()
  { if (currentCallStack == null)
    { currentCallStack = new CallStack();
    }
    return currentCallStack;
  }

  public void incrementProgrammeCounter()
  { programmeCounter++;
  }

  public int getProgrammeCounter()
  { return programmeCounter;
  }

  public void setProgrammeCounter(int newProgrammeCounter)
  { programmeCounter = newProgrammeCounter;
  }

  public void invalidJump(RuntimeValue value)
  { throw new RuntimeException("Invalid jump to " + programmeCounter + " with value \"" + value.toString() + "\" and stack \"" + valueStack.toString() + "\"");
  }

  public void addFrame(LambdaValue function)
  { // Programme counter past current position
    callStack= new FunctionalListCons<>(new CallFrame(function, valueStack, programmeCounter+1), callStack);
    valueStack = new FunctionalListNull<>();
    programmeCounter = 0;
  }

  public ContinuationValue getContinuation()
  { return new ContinuationValue(callStack, valueStack, programmeCounter);
  }

  public void setContinuation(ContinuationValue continuation)
  { callStack = continuation.getCallStack();
    valueStack = continuation.getValueStack();
    programmeCounter = continuation.getProgrammeCounter();
  }

  public void addHandler(LambdaValue handler)
  { if (programmeCounter != 0 || ! valueStack.isEmpty()) throw new InternalCompilerException("Was expecting handler at start");
    handlers = new FunctionalListCons<>(new Pair<>(callStack, handler), handlers);
  }

  public LambdaValue restoreHandler()
  { if (handlers.isEmpty()) return null;
    callStack = handlers.head().getFirst();
    LambdaValue returnValue = handlers.head().getSecond();
    handlers = handlers.tail();
    return returnValue;
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

  public void pushValue(RuntimeValue value) { valueStack = new FunctionalListCons<>(value, valueStack); }
  public RuntimeValue popValue()
  { RuntimeValue returnValue = valueStack.head();
    valueStack = valueStack.tail();
    return returnValue;
  }

  public static RuntimeValue start(LambdaValue function, RuntimeValue arguments)
  { CallStack savedCallStack = currentCallStack;
    try
    { currentCallStack = new CallStack();
      return currentCallStack.run(function, arguments);
    } finally
    { currentCallStack = savedCallStack;
    }
  }

  public RuntimeValue run(LambdaValue function, RuntimeValue arguments)
  { programmeCounter = -1; // addFrame will increment it to 0;
    addFrame(function);
    RuntimeValue currentValue = arguments;
    while (! callStack.isEmpty())
    { CallFrame currentFrame = callStack.head();
      valueStack = currentFrame.getValueStack();
      programmeCounter = currentFrame.getProgrammeCounter();
      callStack = callStack.tail();
      if (Compiler.profile)
      { currentValue = profile(currentFrame, currentValue);
      } else
      { currentValue = currentFrame.continueWith(currentValue);
      }

      // Tail calls
      while (currentValue instanceof CallValue)
      { valueStack = new FunctionalListNull<>();
        programmeCounter = 0;
        if (Compiler.profile)
        { currentValue = profile((CallValue) currentValue);
        } else
        { currentValue = ((CallValue) currentValue).call();
        }
      }
    }
    for (Map. Entry<String, Long> entry : profile.entrySet())
    { System.err.println(entry.getValue() + ", " + entry.getKey());
    }
    return currentValue;
  }

  public void preprofile(String key)
  { if (profile.containsKey(key))
    { sumTime = profile.get(key);
    } else
    { sumTime = 0;
    }
    startTime = System.nanoTime();
  }
  public void postprofile(String key)
  { endTime = System.nanoTime();
    profile.put(key, sumTime + (endTime-startTime));
  }
  public RuntimeValue profile(CallValue call)
  { preprofile(functionString(call));
     RuntimeValue returnValue = call.call();
     postprofile(functionString(call));
     return returnValue;
  }
  public RuntimeValue profile(CallFrame call, RuntimeValue value)
  { preprofile(functionString(call));
     RuntimeValue returnValue = call.continueWith(value);
     postprofile(functionString(call));
     return returnValue;
  }

  public static String functionString(LambdaValue function, int programmeCounter)
  { return function.getClass().toString() + ":" + programmeCounter;
  }
  public static String functionString(CallFrame frame)
  { return functionString(frame.getFunction(), frame.getProgrammeCounter());
  }
  public static String functionString(RuntimeValue frame)
  { return functionString(((CallValue) frame).getFunction(), 0);
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
