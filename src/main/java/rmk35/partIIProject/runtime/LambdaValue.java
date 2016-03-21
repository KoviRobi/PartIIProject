package rmk35.partIIProject.runtime;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;

import java.util.List;
import java.util.function.Function;

public abstract class LambdaValue implements RuntimeValue, Function<RuntimeValue, RuntimeValue>
{ @Override
  public SourceInfo getSourceInfo() { return null; }

  public boolean equal(RuntimeValue other) { return false; }
  public boolean eqv(RuntimeValue other) { return this == other; }
  public boolean eq(RuntimeValue other) { return this == other; }

  public abstract RuntimeValue apply(RuntimeValue argument);

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { throw new InternalCompilerException("I don't know how to generate ByteCode for LambdaValue yet");
  }

  @Override
  public Object toJavaValue()
  { return this;
  }
}
