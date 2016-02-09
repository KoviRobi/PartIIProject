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
import rmk35.partIIProject.backend.instructions.StringConstantInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.types.VoidType;
import rmk35.partIIProject.backend.instructions.types.ObjectType;

import lombok.Value;

@Value
public class IdentifierValue implements PrimitiveValue
{ String value;
  SourceInfo sourceInfo;

  @Deprecated
  public IdentifierValue(String value)
  { this(value, null);
  }
  public IdentifierValue(String value, SourceInfo sourceInfo)
  { this.value = value.intern();
    this.sourceInfo = sourceInfo;
  }

  public static String decodeAbbreviationPrefix(String prefix)
  { if ("'".equals(prefix)) return "quote";
    if ("`".equals(prefix)) return "quasiquote";
    if (",".equals(prefix)) return "unquote";
    if (",@".equals(prefix)) return "unquote-splicing";
    throw new InternalCompilerException("Unable to decode abbreviation prefix: " + prefix);
  }

  public String getValue() { return value; }
  public SourceInfo getSourceInfo() { return sourceInfo; }

  @Override
  public boolean eq(RuntimeValue other)
  { return other instanceof IdentifierValue
        && value.equals(((IdentifierValue)other).value);
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
  { method.addInstruction(new CommentPseudoInstruction("ByteCode for " + IdentifierValue.class.getName()));
    method.addInstruction(new NewObjectInstruction(IdentifierValue.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new StringConstantInstruction(value));
    method.addInstruction(new NonVirtualCallInstruction(new VoidType(), IdentifierValue.class.getName().replace('.', '/') + "/<init>", new ObjectType(String.class)));
  }
}
