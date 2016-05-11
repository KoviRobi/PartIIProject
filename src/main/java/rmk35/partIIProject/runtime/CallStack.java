package rmk35.partIIProject.runtime;

import rmk35.partIIProject.Compiler;
import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.utility.Pair;
import rmk35.partIIProject.utility.FunctionalList;
import rmk35.partIIProject.utility.FunctionalListNull;
import rmk35.partIIProject.utility.FunctionalListCons;

import rmk35.partIIProject.runtime.libraries.DynamicPointPathChain;

import rmk35.partIIProject.middle.ASTVisitor;

import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import lombok.Value;

public class CallStack
{ FunctionalList<CallFrame> callStack = new FunctionalListNull<>();
  FunctionalList<RuntimeValue> valueStack = new FunctionalListNull<>();
  FunctionalList<ContinuationValue> handlers = new FunctionalListNull<>();
  FunctionalList<Pair<LambdaValue, LambdaValue>> dynamicPoints = new FunctionalListNull<>();
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
  { return new ContinuationValue(callStack, dynamicPoints, valueStack, programmeCounter);
  }

  public void setContinuation(ContinuationValue continuation)
  { callStack = continuation.getCallStack();
    valueStack = continuation.getValueStack();
    programmeCounter = -1; // The next addFrame will increment it to zero
    addFrame(new DynamicPointPathChain(dynamicPointTravel(dynamicPoints, continuation.getDynamicPoints())));
    dynamicPoints = continuation.getDynamicPoints();
  }

  public void addHandler()
  { if (programmeCounter != 0 || ! valueStack.isEmpty()) throw new InternalCompilerException("Was expecting handler at start");
    handlers = new FunctionalListCons<>(getContinuation(), handlers);
  }

  public boolean restoreHandler()
  { if (handlers.isEmpty()) return false;
    setContinuation(handlers.head());
    handlers = handlers.tail();
    return true;
  }

  public void addDynamicPoint(LambdaValue before, LambdaValue after)
  { dynamicPoints = new FunctionalListCons<>(new Pair<>(before, after), dynamicPoints);
  }

  public void removeDynamicPoint()
  { dynamicPoints = dynamicPoints.tail();
  }

  public FunctionalList<Pair<LambdaValue, LambdaValue>> getDynamicPoints()
  { return dynamicPoints;
  }

  public void setDynamicPoints(FunctionalList<Pair<LambdaValue, LambdaValue>> dynamicPoints)
  { this.dynamicPoints = dynamicPoints;
  }

  public List<Pair<LambdaValue, FunctionalList<Pair<LambdaValue, LambdaValue>>>>
  dynamicPointTravel(FunctionalList<Pair<LambdaValue, LambdaValue>> afters, FunctionalList<Pair<LambdaValue, LambdaValue>> befores)
  { List<Pair<LambdaValue, FunctionalList<Pair<LambdaValue, LambdaValue>>>> returnValue = new ArrayList<>();
    List<Pair<LambdaValue, LambdaValue>> aftersList = afters.toJavaList();
    List<Pair<LambdaValue, LambdaValue>> beforesList = befores.toJavaList();
    ListIterator<Pair<LambdaValue, LambdaValue>> aftersIterator = aftersList.listIterator(aftersList.size());
    ListIterator<Pair<LambdaValue, LambdaValue>> beforesIterator = beforesList.listIterator(beforesList.size());
    int aftersDifferenceSize = 0;
    int beforesDifferenceSize = 0;
    while (aftersIterator.hasPrevious() && beforesIterator.hasPrevious())
    { if (! (aftersIterator.previous().equals(beforesIterator.previous()))) break;
    }
    while (aftersIterator.hasPrevious()) { aftersDifferenceSize++; aftersIterator.previous(); }
    while (beforesIterator.hasPrevious()) { beforesDifferenceSize++; beforesIterator.previous(); }
    for (int i = 0; i < aftersDifferenceSize; i++)
    { returnValue.add(new Pair<>(afters.head().getSecond(), afters));
      afters = afters.tail();
    }
    List<Pair<LambdaValue, FunctionalList<Pair<LambdaValue, LambdaValue>>>> beforesWrongOrder = new ArrayList<>();
    for (int i = 0; i < beforesDifferenceSize; i++)
    { beforesWrongOrder.add(new Pair<>(befores.head().getFirst(), befores));
      befores = befores.tail();
    }
    for (Pair<LambdaValue, FunctionalList<Pair<LambdaValue, LambdaValue>>> before : beforesWrongOrder)
    { returnValue.add(before);
    }
    return returnValue;
  }

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
