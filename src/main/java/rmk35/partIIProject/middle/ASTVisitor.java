package rmk35.partIIProject.middle;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

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
import rmk35.partIIProject.runtime.ThrowableValue;

public abstract class ASTVisitor<T>
{ public abstract T visit(ConsValue list) throws SyntaxErrorException;
  public abstract T visit(IdentifierValue identifier) throws SyntaxErrorException;
  public abstract T visit(NullValue nil) throws SyntaxErrorException;
  // This is subclass only access modifier, as below explicitly lists all literals,
  // and if we add a new literal then this way we will get a static error rather than a dynamic
  protected abstract T visit(SelfquotingValue object) throws SyntaxErrorException;

  // SelfquotingValue subtypes, in case we want to specialise
  public T visit(BooleanValue booln) throws SyntaxErrorException { return visit((SelfquotingValue)booln); }
  public T visit(BytevectorValue bytevector) throws SyntaxErrorException { return visit((SelfquotingValue)bytevector); }
  public T visit(CharacterValue character) throws SyntaxErrorException { return visit((SelfquotingValue)character); }
  public T visit(NumberValue number) throws SyntaxErrorException { return visit((SelfquotingValue)number); }
  public T visit(StringValue string) throws SyntaxErrorException { return visit((SelfquotingValue)string); }
  public T visit(VectorValue vector) throws SyntaxErrorException { return visit((SelfquotingValue)vector); }
  
  // RuntimeValue subtypes, usually an error
  public T visit(LambdaValue vector) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state"); }
  public T visit(ThrowableValue vector) throws SyntaxErrorException { throw new InternalCompilerException("Unexpected state"); }
}
