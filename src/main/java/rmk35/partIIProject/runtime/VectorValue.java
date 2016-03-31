package rmk35.partIIProject.runtime;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.NewReferenceArrayInstruction;
import rmk35.partIIProject.backend.instructions.IntegerConstantInstruction;
import rmk35.partIIProject.backend.instructions.ReferenceArrayStoreInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueArrayType;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class VectorValue extends SelfquotingValue
{ RuntimeValue[] value;
  SourceInfo sourceInfo;

  @Deprecated
  public VectorValue(RuntimeValue[] value)
  { this(value, null);
  }
  public VectorValue(RuntimeValue[] value, SourceInfo sourceInfo)
  { this.value = value;
    this.sourceInfo = sourceInfo;
  }
  public VectorValue(List<RuntimeValue> value, SourceInfo sourceInfo)
  { this.value = new RuntimeValue[value.size()];
    int index = 0;
    for (RuntimeValue v : value)
    { this.value[index] = v;
      index++;
    }
    this.sourceInfo = sourceInfo;
  }

  public RuntimeValue[] getValue() { return value; }
  public SourceInfo getSourceInfo() { return sourceInfo; }

  @Override
  public boolean eq(RuntimeValue other)
  { return other instanceof VectorValue
        && getValue() == ((VectorValue)other).getValue();
  }

  @Override
  public boolean eqv(RuntimeValue other) { return eq(other); }
  @Override
  public boolean equal(RuntimeValue other)
  { if (! (other instanceof VectorValue)) return false;
    return Arrays.equals(value, ((VectorValue) other).getValue());
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("ByteCode for " + VectorValue.class.getName()));
    method.addInstruction(new NewObjectInstruction(VectorValue.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new IntegerConstantInstruction(value.length));
    method.addInstruction(new NewReferenceArrayInstruction(RuntimeValue.class));
    for (int i = 0; i < value.length; i++)
    { method.addInstruction(new DupInstruction()); // Invariant, array on top of stack
      method.addInstruction(new IntegerConstantInstruction(i));
      value[i].generateByteCode(mainClass, outputClass, method);
      method.addInstruction(new ReferenceArrayStoreInstruction());
    }
    method.addInstruction(new NonVirtualCallInstruction(voidType, VectorValue.class.getName().replace('.', '/') + "/<init>", runtimeValueArrayType));
  }

  @Override
  public String toString()
  { StringBuilder returnValue = new StringBuilder("#(");
    for (int i = 0; i < value.length; i++)
    { if (i > 0) returnValue.append(" ");
      returnValue.append(value[i].toString());
    }
    returnValue.append(")");
    return returnValue.toString();
  }

  @Override
  public Object[] toJavaValue()
  { Object[] returnValue = new Object[value.length];
    for (int i = 0; i < value.length; i++)
    { returnValue[i] = value[i].toJavaValue();
    }
    return returnValue;
  }
}
