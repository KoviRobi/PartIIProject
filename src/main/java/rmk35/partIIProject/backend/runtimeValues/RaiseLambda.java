package rmk35.partIIProject.backend.runtimeValues;

public class RaiseLambda extends UnaryLambda
{ RuntimeValue run(RuntimeValue first)
  { throw new ThrowableValue(first);
  }
}
