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
import rmk35.partIIProject.backend.instructions.IntegerConstantInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.VoidType;
import rmk35.partIIProject.backend.instructions.types.IntegerType;

import lombok.Value;

@Value
public class NumberValue implements SelfquotingValue
{ Integer value;
  SourceInfo sourceInfo;

  @Deprecated
  public NumberValue(Integer value)
  { this(value, null);
  }
  @Deprecated
  public NumberValue(int value)
  { this(value, null);
  }
   public NumberValue(Integer value, SourceInfo sourceInfo)
  { this.value = value;
    this.sourceInfo = sourceInfo;
  }
  public NumberValue(String value, SourceInfo sourceInfo)
  { this.value = Integer.decode(value);
    this.sourceInfo = sourceInfo;
  }

  public Integer getValue() { return value; }
  public SourceInfo getSourceInfo() { return sourceInfo; }
  @Override
  public String toString() { return value.toString(); }

  public boolean equal(RuntimeValue other)
  { if (other instanceof NumberValue)
    { return value.equals(((NumberValue)other).value);
    } else
    { return false;
    }
  }

  public boolean eqv(RuntimeValue other)
  { return equal(other);
  }

  public boolean eq(RuntimeValue other)
  { return equal(other);
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("ByteCode for " + NumberValue.class.getName()));
    method.addInstruction(new NewObjectInstruction(NumberValue.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new IntegerConstantInstruction(value));
    method.addInstruction(new NonVirtualCallInstruction(new VoidType(), NumberValue.class.getName().replace('.', '/') + "/<init>", new IntegerType()));
  
  }
}
