package rmk35.partIIProject.runtime;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;

import lombok.Value;

@Value
public class CallValue implements RuntimeValue
{ // Encapsulate a call to a LambdaValue
  LambdaValue function;
  RuntimeValue arguments;

  public static CallValue create(RuntimeValue function, RuntimeValue arguments)
  { return new CallValue((LambdaValue) function, arguments);
  }

  public CallValue(LambdaValue function, RuntimeValue arguments)
  { this.function = function;
    this.arguments = arguments;
  }

  public RuntimeValue call()
  { return function.apply(arguments);
  }

  @Override
  public SourceInfo getSourceInfo() { return null; }

  public boolean equal(RuntimeValue other) { throw new InternalCompilerException("A CallValue is an internal data structure, it should not be externally visible."); }
  public boolean eqv(RuntimeValue other) { throw new InternalCompilerException("A CallValue is an internal data structure, it should not be externally visible."); }
  public boolean eq(RuntimeValue other) { throw new InternalCompilerException("A CallValue is an internal data structure, it should not be externally visible."); }
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method) { throw new InternalCompilerException("A CallValue is an internal data structure, it should not be externally visible."); }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  @Override
  public Object toJavaValue() { throw new InternalCompilerException("A CallValue is an internal data structure, it should not be externally visible."); }

  @Override
  public boolean mutable()
  { return false;
  }
}
