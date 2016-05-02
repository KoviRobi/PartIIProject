package rmk35.partIIProject.runtime.numbers;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.SelfquotingValue;
import rmk35.partIIProject.runtime.BooleanValue;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.StringConstantInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.stringType;

import java.util.regex.Pattern;

public abstract class NumberValue extends SelfquotingValue
{ SourceInfo sourceInfo;
  static Pattern decimalTen = Pattern.compile(
  "[0-9]+e[+\\-]?[0-9]+" + "|" +
  "\\.[0-9]+(e[+\\-]?[0-9]+)?" + "|" +
  "[0-9]+\\.[0-9]*(e[+\\-]?[0-9]+)?"
  );

  public static RuntimeValue parse(String value) { return parse(value, 10, null); }
  public static RuntimeValue parse(String value, int radix) { return parse(value, radix, null); }
  public static RuntimeValue parse(String value, int radix, SourceInfo sourceInfo)
  { RuntimeValue returnValue;
    // Very primitive at the moment
    try
    { if (decimalTen.matcher(value).matches() && radix == 10)
      { returnValue = new RealValue(Double.valueOf(value));
      } else
      { returnValue = new IntegerValue(Long.valueOf(value, radix));
      }
    } catch (NumberFormatException e)
    { returnValue = new BooleanValue(false);
    }
    return returnValue;
  }

  public SourceInfo getSourceInfo() { return sourceInfo; }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("ByteCode for " + NumberValue.class.getName()));
    method.addInstruction(new StringConstantInstruction(toString()));
    method.addInstruction(new StaticCallInstruction(runtimeValueType, NumberValue.class, "parse", stringType));
  }

  @Override public boolean eq(RuntimeValue other) { return eqv(other); }
  @Override public boolean equal(RuntimeValue other) { return eqv(other); }

  public abstract String toString();
  public abstract String toString(int radix);

  public abstract boolean isExact();
  public abstract boolean numberEquals(NumberValue other);
  public abstract boolean numberLessThan(NumberValue other);
  public abstract boolean numberGreaterThan(NumberValue other);
  public abstract boolean numberLessThanOrEquals(NumberValue other);
  public abstract boolean numberGreaterThanOrEquals(NumberValue other);
  public abstract boolean isZero();
  public abstract boolean isPositive();
  public abstract boolean isNegative(); // NaN is neither
  public abstract boolean isEven();
  public abstract boolean isOdd(); // Abstract as well, as inexact is neither
  public abstract NumberValue toExact();
  public abstract NumberValue toInexact();
  public abstract NumberValue add(NumberValue other);
  public abstract NumberValue subtract(NumberValue other);
  public abstract NumberValue multiply(NumberValue other);
  public abstract NumberValue divide(NumberValue other);
}
