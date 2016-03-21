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
import rmk35.partIIProject.backend.instructions.types.CharacterType;

import lombok.Value;

@Value
public class CharacterValue implements SelfquotingValue
{ char value;
  SourceInfo sourceInfo;

  @Deprecated
  public CharacterValue(char value)
  { this(value, null);
  }
  public CharacterValue(char value, SourceInfo sourceInfo)
  { this.value = value;
    this.sourceInfo = sourceInfo;
  }
  public CharacterValue(String text, SourceInfo sourceInfo) throws SyntaxErrorException
  { if (text.length() == 3)
    { value = text.charAt(3);
    } else if (text.startsWith("#\\x"))
    { value = Character.valueOf((char)Integer.parseInt(text.substring(3), 16));
    } else if (text.equals("#\\alarm"))
    { value = '\u0008';
    } else if (text.equals("#\\backspace"))
    { value = '\b';
    } else if (text.equals("#\\delete"))
    { value = '\u007F';
    } else if (text.equals("#\\escape"))
    { value = '\u001B';
    } else if (text.equals("#\\newline"))
    { value = '\n';
    } else if (text.equals("#\\null"))
    { value = '\0';
    } else if (text.equals("#\\return"))
    { value = '\r';
    } else if (text.equals("#\\space"))
    { value = ' ';
    } else if (text.equals("#\\tab"))
    { value = '\t';
    } else
    { throw new SyntaxErrorException("Failed to parse \"" + text + "\"", sourceInfo);
    }
    this.sourceInfo = sourceInfo;
  }

  public char getValue() { return value; }
  public SourceInfo getSourceInfo() { return sourceInfo; }

  @Override
  public boolean eq(RuntimeValue other)
  { return other instanceof CharacterValue
        && getValue() == ((CharacterValue)other).getValue();
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
  { method.addInstruction(new CommentPseudoInstruction("ByteCode for " + CharacterValue.class.getName()));
    method.addInstruction(new NewObjectInstruction(CharacterValue.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new IntegerConstantInstruction(value));
    method.addInstruction(new NonVirtualCallInstruction(new VoidType(), CharacterValue.class.getName().replace('.', '/') + "/<init>", new CharacterType()));
  }

  @Override
  public Character toJavaValue()
  { return value;
  }
}
