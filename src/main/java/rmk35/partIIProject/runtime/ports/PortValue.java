package rmk35.partIIProject.runtime.ports;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.PrimitiveValue;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;

import java.io.Closeable;
import java.io.IOException;

public abstract class PortValue implements PrimitiveValue
{ SourceInfo sourceInfo;
  Closeable port;
  RuntimeValue nextValue = null;

  public PortValue(Closeable port)
  { this(port, null);
  }
  public PortValue(Closeable port, SourceInfo sourceInfo)
  { this.port = port;
    this.sourceInfo = sourceInfo;
  }

  public SourceInfo getSourceInfo() { return sourceInfo; }

  @Override
  public boolean eq(RuntimeValue other)
  { return other instanceof PortValue && port.equals(((PortValue) other).port); // This is unspecified
  }

  @Override
  public boolean eqv(RuntimeValue other) { return eq(other); }
  @Override
  public boolean equal(RuntimeValue other) { return eq(other); }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { throw new UnsupportedOperationException("Don't know how to reproduce ports. What if they can't (e.g. TCP, no tty, or file that has been since deleted)?");
  }

  @Override
  public String toString() { return "#<port: " + port.toString() + ">"; }

  @Override
  public Object toJavaValue()
  { return port;
  }

  @Override
  public boolean mutable()
  { return false;
  }

  public void close() throws IOException
  { port.close();
  }

  public RuntimeValue getNext()
  { return nextValue;
  }

  public void setNext(RuntimeValue nextValue)
  { this.nextValue = nextValue;
  }
}
