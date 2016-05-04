package rmk35.partIIProject.runtime.numbers;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;

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

public class IntegerValue extends RationalValue
{ long value;

  public IntegerValue(long value)
  { this.value = value;
  }

  public IntegerValue(int value)
  { this.value = value;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("ByteCode for " + IntegerValue.class.getName()));
    method.addInstruction(new NewObjectInstruction(IntegerValue.class));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new IntegerConstantInstruction(value));
    method.addInstruction(new NonVirtualCallInstruction(voidType, IntegerValue.class, "<init>", integerType));
  }

  @Override
  public String toString()
  { return Long.toString(value);
  }

  @Override
  public String toString(int radix)
  { return Long.toString(value, radix);
  }

  @Override
  public Number toJavaValue()
  { return value;
  }

  @Override
  public boolean isExact()
  { return true;
  }

  @Override
  public boolean eqv(RuntimeValue other)
  { return other instanceof IntegerValue && value == ((IntegerValue) other).value;
  }

  @Override
  public boolean numberEquals(NumberValue other)
  { return other instanceof IntegerValue ? value == ((IntegerValue) other).value : new RealValue(value).numberEquals(other);
  }

  @Override
  public boolean numberLessThan(NumberValue other)
  { return other instanceof IntegerValue ? value < ((IntegerValue) other).value : new RealValue(value).numberLessThan(other);
  }

  @Override
  public boolean numberGreaterThan(NumberValue other)
  { return other instanceof IntegerValue ? value > ((IntegerValue) other).value : new RealValue(value).numberGreaterThan(other);
  }

  @Override
  public boolean numberLessThanOrEquals(NumberValue other)
  { return other instanceof IntegerValue ? value <= ((IntegerValue) other).value : new RealValue(value).numberLessThanOrEquals(other);
  }

  @Override
  public boolean numberGreaterThanOrEquals(NumberValue other)
  { return other instanceof IntegerValue ? value >= ((IntegerValue) other).value : new RealValue(value).numberGreaterThanOrEquals(other);
  }

  @Override
  public boolean isZero()
  { return value == 0;
  }

  @Override
  public boolean isPositive()
  { return value > 0;
  }

  @Override
  public boolean isNegative()
  { return value < 0;
  }

  @Override
  public boolean isEven()
  { return value % 2 == 0;
  }

  @Override
  public boolean isOdd()
  { return value % 2 == 1;
  }

  @Override
  public NumberValue toExact()
  { return this;
  }

  @Override
  public NumberValue toInexact()
  { return new RealValue(value);
  }

  @Override
  public NumberValue add(NumberValue other)
  { return other instanceof IntegerValue ? new IntegerValue(value+((IntegerValue) other).value) : new RealValue(value).add(other);
  }

  @Override
  public NumberValue subtract(NumberValue other)
  { return other instanceof IntegerValue ? new IntegerValue(value-((IntegerValue) other).value) : new RealValue(value).subtract(other);
  }

  @Override
  public NumberValue multiply(NumberValue other)
  { return other instanceof IntegerValue ? new IntegerValue(value*((IntegerValue) other).value) : new RealValue(value).multiply(other);
  }

  @Override
  public NumberValue divide(NumberValue other)
  { return other instanceof IntegerValue ? new IntegerValue(value/((IntegerValue) other).value) : new RealValue(value).divide(other);
  }
}
