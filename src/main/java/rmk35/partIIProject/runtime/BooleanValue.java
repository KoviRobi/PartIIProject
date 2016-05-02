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
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.booleanType;

import lombok.Value;

@Value
public class BooleanValue extends SelfquotingValue
{ boolean value;
  SourceInfo sourceInfo;

  public BooleanValue(boolean value)
  { this(value, null);
  }
  public BooleanValue(String value, SourceInfo sourceInfo)
  { this.value = "#t".equals(value) || "#true".equals(value);
    this.sourceInfo = null;
  }
  public BooleanValue(boolean value, SourceInfo sourceInfo)
  { this.value = value;
    this.sourceInfo = null;
  }

  public boolean getValue() { return value; }
  public SourceInfo getSourceInfo() {return sourceInfo; }
  @Override
  public String writeString() { return "#" + value; }
  @Override
  public String displayString() { return "#" + value; }

  @Override
  public boolean eq(RuntimeValue other)
  { return other instanceof BooleanValue
        && getValue() == ((BooleanValue)other).getValue();
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
  { method.addInstruction(new CommentPseudoInstruction("ByteCode for " + BooleanValue.class.getName()));
    method.addInstruction(new NewObjectInstruction(BooleanValue.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new IntegerConstantInstruction(value? 1 : 0));
    method.addInstruction(new NonVirtualCallInstruction(voidType, BooleanValue.class, "<init>", booleanType));
  }

  @Override
  public Boolean toJavaValue()
  { return value;
  }
}
