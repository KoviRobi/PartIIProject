package rmk35.partIIProject.runtime;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;

public class ErrorValue implements RuntimeValue
{ StringValue message;
  RuntimeValue irritants;
  RuntimeValue nextValue = null;

  public ErrorValue(RuntimeValue value)
  { ConsValue first = ((ConsValue) value);
    message = (StringValue) first.getCar();
    irritants = first.getCdr();
  }

  public StringValue getMessage() { return message; }
  public RuntimeValue getIrritants() { return irritants; }

  @Override
  public SourceInfo getSourceInfo() { return null; }

  @Override
  public boolean eq(RuntimeValue other) { return false; }
  @Override
  public boolean eqv(RuntimeValue other) { return false; }
  @Override
  public boolean equal(RuntimeValue other) { return false; }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { throw new InternalCompilerException("I don't know how to generate ByteCode for ErrorValue yet");
  }

  @Override
  public Object toJavaValue()
  { return this;
  }

  @Override
  public boolean mutable()
  { return false;
  }

  public RuntimeValue getNext()
  { return nextValue;
  }

  public void setNext(RuntimeValue nextValue)
  { this.nextValue = nextValue;
  }
}
