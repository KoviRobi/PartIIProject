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
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;

import java.util.ArrayList;

import lombok.Value;

@Value
public class NullValue implements PrimitiveValue
{ SourceInfo sourceInfo;

  @Deprecated
  public NullValue()
  { this(null);
  }
  public NullValue(SourceInfo sourceInfo)
  { this.sourceInfo = sourceInfo;
  }

  public SourceInfo getSourceInfo() { return sourceInfo; }

  @Override
  public boolean eq(RuntimeValue other)
  { return other instanceof NullValue;
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
  { method.addInstruction(new CommentPseudoInstruction("ByteCode for " + NullValue.class.getName()));
    method.addInstruction(new NewObjectInstruction(NullValue.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new NonVirtualCallInstruction(voidType, NullValue.class.getName().replace('.', '/') + "/<init>"));
  }

  @Override
  public String toString() { return "()"; }

  @Override
  public Object toJavaValue()
  { return new ArrayList<>();
  }

  @Override
  public boolean mutable()
  { return false;
  }
}
