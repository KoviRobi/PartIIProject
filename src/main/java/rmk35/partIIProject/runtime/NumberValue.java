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
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.integerType;

import lombok.Value;

@Value
public class NumberValue extends SelfquotingValue
{ Integer value;
  SourceInfo sourceInfo;

  public NumberValue(Integer value)
  { this(value, null);
  }
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
    method.addInstruction(new NonVirtualCallInstruction(voidType, NumberValue.class.getName().replace('.', '/') + "/<init>", integerType));
  }

  @Override
  public Integer toJavaValue()
  { return value;
  }
}
