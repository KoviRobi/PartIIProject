package rmk35.partIIProject.middle;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.SelfquotingValue;
// SelfquotingValues
import rmk35.partIIProject.runtime.BooleanValue;
import rmk35.partIIProject.runtime.BytevectorValue;
import rmk35.partIIProject.runtime.CharacterValue;
import rmk35.partIIProject.runtime.NumberValue;
import rmk35.partIIProject.runtime.StringValue;
import rmk35.partIIProject.runtime.VectorValue;
// RuntimeValues
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.TrampolineValue;
import rmk35.partIIProject.runtime.EndOfFileValue;
import rmk35.partIIProject.runtime.ThrowableValue;
import rmk35.partIIProject.runtime.MethodValue;
import rmk35.partIIProject.runtime.ObjectValue;

public abstract class ASTVisitor<T>
{ // This is subclass only access modifier, as below explicitly lists all values,
  // and if we add a new values then this way we will get a static error rather than a dynamic
  protected T visit(RuntimeValue object) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state"); }

  public T visit(ConsValue cons) throws SyntaxErrorException { return visit((RuntimeValue)cons); }
  public T visit(IdentifierValue identifier) throws SyntaxErrorException { return visit((RuntimeValue)identifier); }
  public T visit(NullValue nullValue) throws SyntaxErrorException { return visit((RuntimeValue)nullValue); }
  // This is subclass only access modifier, as below explicitly lists all values,
  // and if we add a new values then this way we will get a static error rather than a dynamic
  protected T visit(SelfquotingValue object) throws SyntaxErrorException { return visit((RuntimeValue)object); }

  // SelfquotingValue subtypes, in case we want to specialise
  public T visit(BooleanValue booln) throws SyntaxErrorException { return visit((SelfquotingValue)booln); }
  public T visit(BytevectorValue bytevector) throws SyntaxErrorException { return visit((SelfquotingValue)bytevector); }
  public T visit(CharacterValue character) throws SyntaxErrorException { return visit((SelfquotingValue)character); }
  public T visit(NumberValue number) throws SyntaxErrorException { return visit((SelfquotingValue)number); }
  public T visit(StringValue string) throws SyntaxErrorException { return visit((SelfquotingValue)string); }
  public T visit(VectorValue vector) throws SyntaxErrorException { return visit((SelfquotingValue)vector); }
  
  // Other RuntimeValue subtypes (these are not PrimitiveValue subtypes), usually an error
  public T visit(LambdaValue lambda) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state"); }
  public T visit(TrampolineValue trampoline) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state"); }
  public T visit(EndOfFileValue eof) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state"); }
  public T visit(ThrowableValue throwable) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state"); }
  public T visit(MethodValue method) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state"); }
  public T visit(ObjectValue object) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state"); }
}
