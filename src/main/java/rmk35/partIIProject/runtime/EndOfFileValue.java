package rmk35.partIIProject.runtime;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.VoidType;

import lombok.Value;

@Value
public class EndOfFileValue implements RuntimeValue
{ SourceInfo sourceInfo;

  @Deprecated
  public EndOfFileValue()
  { this(null);
  }
  public EndOfFileValue(SourceInfo sourceInfo)
  { this.sourceInfo = sourceInfo;
  }

  public SourceInfo getSourceInfo() { return sourceInfo; }

  @Override
  public boolean eq(RuntimeValue other)
  { return other instanceof EndOfFileValue;
  }

  @Override
  public boolean eqv(RuntimeValue other) { return eq(other); }
  @Override
  public boolean equal(RuntimeValue other) { return eq(other); }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("ByteCode for " + EndOfFileValue.class.getName()));
    method.addInstruction(new NewObjectInstruction(EndOfFileValue.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new NonVirtualCallInstruction(new VoidType(), EndOfFileValue.class.getName().replace('.', '/') + "/<init>"));
  }

  @Override
  public String toString() { return "#end-of-file"; }

  @Override
  public Object toJavaValue()
  { return this;
  }
}
