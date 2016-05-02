package rmk35.partIIProject.runtime.libraries;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.ConsValue;

import java.util.List;

public abstract class BinaryConjunctOperator extends BinaryOperator<Boolean>
{ public Boolean combine(Boolean a, Boolean b) { return a && b; }
  public Boolean initial() { return true; }
}
