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
import rmk35.partIIProject.runtime.CallValue;
import rmk35.partIIProject.runtime.EndOfFileValue;
import rmk35.partIIProject.runtime.ThrowableValue;
import rmk35.partIIProject.runtime.ObjectValue;
import rmk35.partIIProject.runtime.ErrorValue;
import rmk35.partIIProject.runtime.UnspecifiedValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

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
  public T visit(LambdaValue lambda) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state: Don't know what to do with a function!"); }
  public T visit(CallValue trampoline) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state: Got a CallValue, this should not be visible!"); }
  public T visit(EndOfFileValue eof) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state: Got an end-of-file value too soon!"); }
  public T visit(ThrowableValue throwable) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state: Don't know what to do with a thrown value!"); }
  public T visit(ObjectValue object) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state: Don't know what to do with an encapsulated Java object!"); }
  public T visit(ErrorValue error) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state: Don't know what to do with an error value!"); }
  public T visit(UnspecifiedValue unspecified) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state: Don't know what to do with an unspecified value!"); }
  public T visit(EnvironmentValue unspecified) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state: Don't know what to do with an environment!"); }
}
