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
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.stringType;

import java.util.Formatter;
import java.util.Set;
import java.util.HashSet;

import lombok.Value;

@Value
public class IdentifierValue implements PrimitiveValue
{ String value;
  SourceInfo sourceInfo;

  public IdentifierValue(String value)
  { this(value, null);
  }
  public IdentifierValue(String value, SourceInfo sourceInfo)
  { this.value = value.intern();
    this.sourceInfo = sourceInfo;
  }

  public String getValue() { return value; }
  public SourceInfo getSourceInfo() { return sourceInfo; }
  @Override
  public String toString() { return value; }

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
    method.addInstruction(new NonVirtualCallInstruction(voidType, IdentifierValue.class.getName().replace('.', '/') + "/<init>", stringType));
  }

  @Override
  public int hashCode()
  { return value.hashCode();
  }

  @Override
  public Object toJavaValue()
  { return this;
  }

  @Override
  public boolean mutable()
  { return false;
  }

  @Override
  public boolean equals(Object other)
  { return other instanceof RuntimeValue && equal((RuntimeValue) other);
  }

  public static String decodeAbbreviationPrefix(String prefix)
  { if ("'".equals(prefix)) return "quote";
    if ("`".equals(prefix)) return "quasiquote";
    if (",".equals(prefix)) return "unquote";
    if (",@".equals(prefix)) return "unquote-splicing";
    throw new InternalCompilerException("Unable to decode abbreviation prefix: " + prefix);
  }

  public static String javaifyName(String name)
  { StringBuilder returnValue = new StringBuilder("scm_");
    for (int i = 0; i < name.length(); i++)
    { if (('a' <= name.codePointAt(i) && name.codePointAt(i) <= 'z') ||
          ('0' <= name.codePointAt(i) && name.codePointAt(i) <= '9') ||
          ('A' <= name.codePointAt(i) && name.codePointAt(i) <= 'Z'))
      { returnValue.appendCodePoint(name.codePointAt(i));
      } else if ('-' == name.codePointAt(i))
      { returnValue.append("_");
      } else
      { returnValue.append("$");
        returnValue.append(String.format("%06X", name.codePointAt(i)));
      }
    }
    return returnValue.toString();
  }

  public static String schemifyName(String name)
  { StringBuilder returnValue = new StringBuilder();
    for (int i = 4; i < name.length(); i++)
    { if (name.codePointAt(i) == '$')
      { returnValue.appendCodePoint(Integer.parseInt(name.substring(i+1, i+7), 16));
        i += 6;
      } else if ('_' == name.codePointAt(i))
      { returnValue.append("-");
      } else
      { returnValue.appendCodePoint(name.codePointAt(i));
      }
    }
    return returnValue.toString();
  }
}
