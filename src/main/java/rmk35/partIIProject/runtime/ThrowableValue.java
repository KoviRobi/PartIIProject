package rmk35.partIIProject.runtime;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;

public class ThrowableValue extends RuntimeException implements RuntimeValue
{ RuntimeValue value;
  RuntimeValue nextValue = null;

  public ThrowableValue(RuntimeValue value)
  { this.value =  value;
  }

  public RuntimeValue getValue()
  { return value;
  }

  @Override
  public SourceInfo getSourceInfo() { return null; }

  @Override
  public boolean eq(RuntimeValue other)
  { return other.eq(value);
  }

  @Override
  public boolean eqv(RuntimeValue other)
  { return other.eqv(value);
  }

  @Override
  public boolean equal(RuntimeValue other)
  { return other.equal(value);
  }

  @Override
  public String getMessage()
  { return "Uncaught raise: " + value.toString();
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { throw new InternalCompilerException("I don't know how to generate ByteCode for ThrowableValue yet");
  }

  @Override
  public Object toJavaValue()
  { return value.toJavaValue();
  }

  @Override
  public boolean mutable()
  { return value.mutable();
  }

  public RuntimeValue getNext()
  { return nextValue;
  }

  public void setNext(RuntimeValue nextValue)
  { this.nextValue = nextValue;
  }
}
