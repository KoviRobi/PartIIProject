package rmk35.partIIProject.runtime.libraries;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.utility.Pair;
import rmk35.partIIProject.utility.FunctionalList;
import rmk35.partIIProject.utility.FunctionalListNull;
import rmk35.partIIProject.utility.FunctionalListCons;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.CallStack;
import rmk35.partIIProject.runtime.CallValue;

import java.util.List;

// At the end, returns the value we got at the start
public class DynamicPointPathChain extends LambdaValue
{ List<Pair<LambdaValue, FunctionalList<Pair<LambdaValue, LambdaValue>>>> path;
  FunctionalList<Pair<LambdaValue, LambdaValue>> oldDynamicPoint;
  RuntimeValue returnValue;

  public DynamicPointPathChain(List<Pair<LambdaValue, FunctionalList<Pair<LambdaValue, LambdaValue>>>> path)
  { this.path = path;
    this.oldDynamicPoint = null;
    this.returnValue = null;
  }

  @Override
  public final RuntimeValue apply(RuntimeValue arguments)
  { return run(new NullValue());
  }

  public RuntimeValue run(RuntimeValue argument)
  { CallStack currentCallStack = CallStack.getCurrentCallStack();
    int programmeCounter = currentCallStack.getProgrammeCounter();
    if (programmeCounter == 0)
    { this.returnValue = argument;
      this.oldDynamicPoint = CallStack.getCurrentCallStack().getDynamicPoints();
    }
    if (path.size() < programmeCounter) currentCallStack.invalidJump(argument);
    if (programmeCounter < path.size())
    { currentCallStack.setDynamicPoints(path.get(programmeCounter).getSecond());
      currentCallStack.addFrame(this);
      return new CallValue(path.get(programmeCounter).getFirst(), new NullValue());
    } else
    { currentCallStack.setDynamicPoints(oldDynamicPoint);
      return returnValue;
    }
  }
}
