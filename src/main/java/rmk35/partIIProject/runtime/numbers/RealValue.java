package rmk35.partIIProject.runtime.numbers;

import rmk35.partIIProject.SyntaxErrorException;
import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.middle.ASTVisitor;

public class RealValue extends ComplexValue
{ double value;

  public RealValue()
  { this.value = 0;
  }
  public RealValue(double value)
  { this.value = value;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  @Override
  public String toString()
  { return Double.toString(value);
  }

  @Override
  public String toString(int radix)
  { if (radix != 10) throw new InternalCompilerException("Radices other than 10 are not supported for reals");
    return Double.toString(value);
  }

  @Override
  public Number toJavaValue()
  { return value;
  }

  @Override
  public boolean isExact()
  { return false;
  }

  @Override
  public boolean eqv(RuntimeValue other)
  { return other instanceof RealValue && value == ((RealValue) other).value;
  }

  @Override
  public boolean numberEquals(NumberValue other)
  { return other instanceof IntegerValue ? value == ((IntegerValue) other).value :
      (other instanceof RealValue && value == ((RealValue) other).value);
  }

  @Override
  public boolean numberLessThan(NumberValue other)
  { return other instanceof IntegerValue ? value == ((IntegerValue) other).value :
      (other instanceof RealValue && value < ((RealValue) other).value);
  }

  @Override
  public boolean numberGreaterThan(NumberValue other)
  { return other instanceof IntegerValue ? value == ((IntegerValue) other).value :
      (other instanceof RealValue && value > ((RealValue) other).value);
  }

  @Override
  public boolean numberLessThanOrEquals(NumberValue other)
  { return other instanceof IntegerValue ? value == ((IntegerValue) other).value :
      (other instanceof RealValue && value <= ((RealValue) other).value);
  }

  @Override
  public boolean numberGreaterThanOrEquals(NumberValue other)
  { return other instanceof IntegerValue ? value == ((IntegerValue) other).value :
      (other instanceof RealValue && value >= ((RealValue) other).value);
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
  { throw new InternalCompilerException("Rationals not supported");
  }

  @Override
  public NumberValue add(NumberValue other)
  { return new RealValue(value+((RealValue) other.toInexact()).value);
  }

  @Override
  public NumberValue subtract(NumberValue other)
  { return new RealValue(value-((RealValue) other.toInexact()).value);
  }

  @Override
  public NumberValue multiply(NumberValue other)
  { return new RealValue(value*((RealValue) other.toInexact()).value);
  }

  @Override
  public NumberValue divide(NumberValue other)
  { return new RealValue(value/((RealValue) other.toInexact()).value);
  }
}
