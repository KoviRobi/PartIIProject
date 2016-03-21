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
import rmk35.partIIProject.backend.instructions.StringConstantInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.VoidType;
import rmk35.partIIProject.backend.instructions.types.ObjectType;

import lombok.Data;

@Data
public class StringValue implements SelfquotingValue
{ String value;
  SourceInfo sourceInfo;

  @Deprecated
  public StringValue(String value)
  { this(value, null);
  }
  public StringValue(String value, SourceInfo sourceInfo)
  { this.value = value;
    this.sourceInfo = sourceInfo;
  }

  public static String decodeParsedString(String parsedString)
  { return parsedString.substring(1, parsedString.lastIndexOf('"'));
  }

  public String getValue() { return value; }
  public SourceInfo getSourceInfo() { return sourceInfo; }
  @Override
  public String toString() { return "\"" + value + "\""; }

  @Override
  public boolean eq(RuntimeValue other)
  { return this == other;
  }

  @Override
  public boolean eqv(RuntimeValue other)
  { return other instanceof StringValue
        && value.equals(((StringValue)other).value);
  }

  @Override
  public boolean equal(RuntimeValue other) { return eq(other); }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("ByteCode for " + StringValue.class.getName()));
    method.addInstruction(new NewObjectInstruction(StringValue.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new StringConstantInstruction(value));
    method.addInstruction(new NonVirtualCallInstruction(new VoidType(), StringValue.class.getName().replace('.', '/') + "/<init>", new ObjectType(String.class)));
  }

  @Override
  public String toJavaValue()
  { return value;
  }
}
