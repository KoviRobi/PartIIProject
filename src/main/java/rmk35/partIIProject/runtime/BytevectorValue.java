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
import rmk35.partIIProject.backend.instructions.NewPrimitiveArrayInstruction;
import rmk35.partIIProject.backend.instructions.IntegerConstantInstruction;
import rmk35.partIIProject.backend.instructions.ByteArrayStoreInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.byteArrayType;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class BytevectorValue extends SelfquotingValue
{ byte[] value;
  SourceInfo sourceInfo;

  public BytevectorValue(byte[] value)
  { this.value = value;
    sourceInfo = null;
  }

  public BytevectorValue(List<Byte> listValue, SourceInfo sourceInfo)
  { value = new byte[listValue.size()];
    int index = 0;
    for(Byte b : listValue)
    { value[index] = b;
      index++;
    }
    this.sourceInfo = sourceInfo;
  }

  public byte[] getValue() { return value; }
  public SourceInfo getSourceInfo() { return sourceInfo; }
  @Override
  public String toString()
  { StringBuilder returnValue = new StringBuilder("#u8(");
    for (int i = 0; i < value.length; i++)
    { if (i > 0) returnValue.append(" ");
      returnValue.append(value[i]);
    }
    returnValue.append(")");
    return returnValue.toString();
  }

  @Override
  public boolean eq(RuntimeValue other)
  { return other instanceof BytevectorValue
        && getValue() == ((BytevectorValue)other).getValue();
  }

  @Override
  public boolean eqv(RuntimeValue other) { return eq(other); }
  @Override
  public boolean equal(RuntimeValue other)
  { if (! (other instanceof BytevectorValue)) return false;
    return Arrays.equals(value, ((BytevectorValue) other).getValue());
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("ByteCode for " + BytevectorValue.class.getName()));
    method.addInstruction(new NewObjectInstruction(BytevectorValue.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new IntegerConstantInstruction(value.length));
    method.addInstruction(new NewPrimitiveArrayInstruction(byte.class));
    for (int i = 0; i < value.length; i++)
    { method.addInstruction(new DupInstruction()); // Invariant, array on top of stack
      method.addInstruction(new IntegerConstantInstruction(i));
      method.addInstruction(new IntegerConstantInstruction(value[i]));
      method.addInstruction(new ByteArrayStoreInstruction());
    }
    method.addInstruction(new NonVirtualCallInstruction(voidType, BytevectorValue.class, "<init>", byteArrayType));
  }

  @Override
  public byte[] toJavaValue()
  { return value;
  }
}
