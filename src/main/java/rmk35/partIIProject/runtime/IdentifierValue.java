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

  @Deprecated
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

  static Set<String> javaReservedKeywords = new HashSet<>();
  static
  { javaReservedKeywords.add("abstract");		javaReservedKeywords.add("assert");
    javaReservedKeywords.add("boolean");		javaReservedKeywords.add("break");
    javaReservedKeywords.add("byte");		javaReservedKeywords.add("case");
    javaReservedKeywords.add("catch");		javaReservedKeywords.add("char");
    javaReservedKeywords.add("class");		javaReservedKeywords.add("const");
    javaReservedKeywords.add("continue");		javaReservedKeywords.add("default");
    javaReservedKeywords.add("do");		javaReservedKeywords.add("double");
    javaReservedKeywords.add("else");		javaReservedKeywords.add("enum");
    javaReservedKeywords.add("extends");		javaReservedKeywords.add("final");
    javaReservedKeywords.add("finally");		javaReservedKeywords.add("float");
    javaReservedKeywords.add("for");		javaReservedKeywords.add("goto");
    javaReservedKeywords.add("if");		javaReservedKeywords.add("implements");
    javaReservedKeywords.add("import");		javaReservedKeywords.add("instanceof");
    javaReservedKeywords.add("int");		javaReservedKeywords.add("interface");
    javaReservedKeywords.add("long");		javaReservedKeywords.add("native");
    javaReservedKeywords.add("new");		javaReservedKeywords.add("package");
    javaReservedKeywords.add("private");		javaReservedKeywords.add("protected");
    javaReservedKeywords.add("public");		javaReservedKeywords.add("return");
    javaReservedKeywords.add("short");		javaReservedKeywords.add("static");
    javaReservedKeywords.add("strictfp");		javaReservedKeywords.add("super");
    javaReservedKeywords.add("switch");		javaReservedKeywords.add("synchronized");
    javaReservedKeywords.add("this");		javaReservedKeywords.add("throw");
    javaReservedKeywords.add("throws");		javaReservedKeywords.add("transient");
    javaReservedKeywords.add("try");		javaReservedKeywords.add("void");
    javaReservedKeywords.add("volatile");		javaReservedKeywords.add("while");
    // Jassmin reserved keywords, directives:
    javaReservedKeywords.add("all");			javaReservedKeywords.add("field");
    javaReservedKeywords.add("end");			javaReservedKeywords.add("limit");
    javaReservedKeywords.add("line");			javaReservedKeywords.add("method");
    javaReservedKeywords.add("source");		javaReservedKeywords.add("var");
    // Jassmin reserved keywords, instructions:
    javaReservedKeywords.add("aaload");		javaReservedKeywords.add("aastore");
    javaReservedKeywords.add("aconst_null");		javaReservedKeywords.add("aload");
    javaReservedKeywords.add("aload_0");		javaReservedKeywords.add("aload_1");
    javaReservedKeywords.add("aload_2");		javaReservedKeywords.add("aload_3");
    javaReservedKeywords.add("anewarray");		javaReservedKeywords.add("areturn");
    javaReservedKeywords.add("arraylength");		javaReservedKeywords.add("astore");
    javaReservedKeywords.add("astore_0");		javaReservedKeywords.add("astore_1");
    javaReservedKeywords.add("astore_2");		javaReservedKeywords.add("astore_3");
    javaReservedKeywords.add("athrow");		javaReservedKeywords.add("baload");
    javaReservedKeywords.add("bastore");		javaReservedKeywords.add("bipush");
    javaReservedKeywords.add("breakpoint");		javaReservedKeywords.add("caload");
    javaReservedKeywords.add("castore");		javaReservedKeywords.add("checkcast");
    javaReservedKeywords.add("d2f");		javaReservedKeywords.add("d2i");
    javaReservedKeywords.add("d2l");		javaReservedKeywords.add("dadd");
    javaReservedKeywords.add("daload");		javaReservedKeywords.add("dastore");
    javaReservedKeywords.add("dcmpg");		javaReservedKeywords.add("dcmpl");
    javaReservedKeywords.add("dconst_0");		javaReservedKeywords.add("dconst_1");
    javaReservedKeywords.add("ddiv");		javaReservedKeywords.add("dload");
    javaReservedKeywords.add("dload_0");		javaReservedKeywords.add("dload_1");
    javaReservedKeywords.add("dload_2");		javaReservedKeywords.add("dload_3");
    javaReservedKeywords.add("dmul");		javaReservedKeywords.add("dneg");
    javaReservedKeywords.add("drem");		javaReservedKeywords.add("dreturn");
    javaReservedKeywords.add("dstore");		javaReservedKeywords.add("dstore_0");
    javaReservedKeywords.add("dstore_1");		javaReservedKeywords.add("dstore_2");
    javaReservedKeywords.add("dstore_3");		javaReservedKeywords.add("dsub");
    javaReservedKeywords.add("dup");		javaReservedKeywords.add("dup2");
    javaReservedKeywords.add("dup2_x1");		javaReservedKeywords.add("dup2_x2");
    javaReservedKeywords.add("dup_x1");		javaReservedKeywords.add("dup_x2");
    javaReservedKeywords.add("f2d");		javaReservedKeywords.add("f2i");
    javaReservedKeywords.add("f2l");		javaReservedKeywords.add("fadd");
    javaReservedKeywords.add("faload");		javaReservedKeywords.add("fastore");
    javaReservedKeywords.add("fcmpg");		javaReservedKeywords.add("fcmpl");
    javaReservedKeywords.add("fconst_0");		javaReservedKeywords.add("fconst_1");
    javaReservedKeywords.add("fconst_2");		javaReservedKeywords.add("fdiv");
    javaReservedKeywords.add("fload");		javaReservedKeywords.add("fload_0");
    javaReservedKeywords.add("fload_1");		javaReservedKeywords.add("fload_2");
    javaReservedKeywords.add("fload_3");		javaReservedKeywords.add("fmul");
    javaReservedKeywords.add("fneg");		javaReservedKeywords.add("frem");
    javaReservedKeywords.add("freturn");		javaReservedKeywords.add("fstore");
    javaReservedKeywords.add("fstore_0");		javaReservedKeywords.add("fstore_1");
    javaReservedKeywords.add("fstore_2");		javaReservedKeywords.add("fstore_3");
    javaReservedKeywords.add("fsub");		javaReservedKeywords.add("getfield");
    javaReservedKeywords.add("getstatic");		javaReservedKeywords.add("goto");
    javaReservedKeywords.add("goto_w");		javaReservedKeywords.add("i2d");
    javaReservedKeywords.add("i2f");		javaReservedKeywords.add("i2l");
    javaReservedKeywords.add("iadd");		javaReservedKeywords.add("iaload");
    javaReservedKeywords.add("iand");		javaReservedKeywords.add("iastore");
    javaReservedKeywords.add("iconst_0");		javaReservedKeywords.add("iconst_1");
    javaReservedKeywords.add("iconst_2");		javaReservedKeywords.add("iconst_3");
    javaReservedKeywords.add("iconst_4");		javaReservedKeywords.add("iconst_5");
    javaReservedKeywords.add("iconst_m1");		javaReservedKeywords.add("idiv");
    javaReservedKeywords.add("if_acmpeq");		javaReservedKeywords.add("if_acmpne");
    javaReservedKeywords.add("if_icmpeq");		javaReservedKeywords.add("if_icmpge");
    javaReservedKeywords.add("if_icmpgt");		javaReservedKeywords.add("if_icmple");
    javaReservedKeywords.add("if_icmplt");		javaReservedKeywords.add("if_icmpne");
    javaReservedKeywords.add("ifeq");		javaReservedKeywords.add("ifge");
    javaReservedKeywords.add("ifgt");		javaReservedKeywords.add("ifle");
    javaReservedKeywords.add("iflt");		javaReservedKeywords.add("ifne");
    javaReservedKeywords.add("ifnonnull");		javaReservedKeywords.add("ifnull");
    javaReservedKeywords.add("iinc");		javaReservedKeywords.add("iload");
    javaReservedKeywords.add("iload_0");		javaReservedKeywords.add("iload_1");
    javaReservedKeywords.add("iload_2");		javaReservedKeywords.add("iload_3");
    javaReservedKeywords.add("imul");		javaReservedKeywords.add("ineg");
    javaReservedKeywords.add("instanceof");		javaReservedKeywords.add("int2byte");
    javaReservedKeywords.add("int2char");		javaReservedKeywords.add("int2short");
    javaReservedKeywords.add("invokenonvirtual");		javaReservedKeywords.add("invokestatic");
    javaReservedKeywords.add("invokevirtual");		javaReservedKeywords.add("ior");
    javaReservedKeywords.add("irem");		javaReservedKeywords.add("ireturn");
    javaReservedKeywords.add("ishl");		javaReservedKeywords.add("ishr");
    javaReservedKeywords.add("istore");		javaReservedKeywords.add("istore_0");
    javaReservedKeywords.add("istore_1");		javaReservedKeywords.add("istore_2");
    javaReservedKeywords.add("istore_3");		javaReservedKeywords.add("isub");
    javaReservedKeywords.add("iushr");		javaReservedKeywords.add("ixor");
    javaReservedKeywords.add("jsr");		javaReservedKeywords.add("jsr_w");
    javaReservedKeywords.add("l2d");		javaReservedKeywords.add("l2f");
    javaReservedKeywords.add("l2i");		javaReservedKeywords.add("ladd");
    javaReservedKeywords.add("laload");		javaReservedKeywords.add("land");
    javaReservedKeywords.add("lastore");		javaReservedKeywords.add("lcmp");
    javaReservedKeywords.add("lconst_0");		javaReservedKeywords.add("lconst_1");
    javaReservedKeywords.add("ldc");		javaReservedKeywords.add("ldc_w");
    javaReservedKeywords.add("ldiv");		javaReservedKeywords.add("lload");
    javaReservedKeywords.add("lload_0");		javaReservedKeywords.add("lload_1");
    javaReservedKeywords.add("lload_2");		javaReservedKeywords.add("lload_3");
    javaReservedKeywords.add("lmul");		javaReservedKeywords.add("lneg");
    javaReservedKeywords.add("lookupswitch");		javaReservedKeywords.add("lor");
    javaReservedKeywords.add("lrem");		javaReservedKeywords.add("lreturn");
    javaReservedKeywords.add("lshl");		javaReservedKeywords.add("lshr");
    javaReservedKeywords.add("lstore");		javaReservedKeywords.add("lstore_0");
    javaReservedKeywords.add("lstore_1");		javaReservedKeywords.add("lstore_2");
    javaReservedKeywords.add("lstore_3");		javaReservedKeywords.add("lsub");
    javaReservedKeywords.add("lushr");		javaReservedKeywords.add("lxor");
    javaReservedKeywords.add("monitorenter");		javaReservedKeywords.add("monitorexit");
    javaReservedKeywords.add("multianewarray");		javaReservedKeywords.add("new");
    javaReservedKeywords.add("newarray");		javaReservedKeywords.add("nop");
    javaReservedKeywords.add("pop");		javaReservedKeywords.add("pop2");
    javaReservedKeywords.add("putfield");		javaReservedKeywords.add("putstatic");
    javaReservedKeywords.add("ret");		javaReservedKeywords.add("return");
    javaReservedKeywords.add("saload");		javaReservedKeywords.add("sastore");
    javaReservedKeywords.add("sipush");		javaReservedKeywords.add("swap");
    javaReservedKeywords.add("tableswitch");
  }
  static Formatter formatter = new Formatter();
  public static String javaifyName(String name)
  { StringBuilder returnValue = new StringBuilder("scm");
    for (int i = 0; i < name.length(); i++)
    { if (('a' < name.codePointAt(i) && name.codePointAt(i) < 'z') ||
          (javaReservedKeywords.contains(name) && i == 0))
      { returnValue.appendCodePoint(name.codePointAt(i));
      } else
      { returnValue.append("X");
        returnValue.append(formatter.format("%06x", name.codePointAt(i)));
      }
    }
    return returnValue.toString();
  }

  public static String schemifyName(String name)
  { StringBuilder returnValue = new StringBuilder();
    for (int i = 3; i < name.length(); i++)
    { if (name.codePointAt(i) == 'X')
      { returnValue.appendCodePoint(Integer.parseInt(name.substring(i+1, i+7), 16));
        i += 6;
      } else
      { returnValue.appendCodePoint(name.codePointAt(i));
      }
    }
    return returnValue.toString();
  }
}
