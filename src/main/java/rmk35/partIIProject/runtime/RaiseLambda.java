package rmk35.partIIProject.runtime;

public class RaiseLambda extends UnaryLambda
{ RuntimeValue run(RuntimeValue first)
  { throw new ThrowableValue(first);
  }
}
