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
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;

import java.util.List;
import java.util.ArrayList;

import lombok.Data;

@Data
public class ConsValue implements PrimitiveValue
{ RuntimeValue car;
  RuntimeValue cdr;
  SourceInfo sourceInfo;

  @Deprecated
  public ConsValue(RuntimeValue car, RuntimeValue cdr)
  { this(car, cdr, null);
  }
  public ConsValue(RuntimeValue car, RuntimeValue cdr, SourceInfo sourceInfo)
  { this.car = car;
    this.cdr = cdr;
    this.sourceInfo = sourceInfo;
  }

  public RuntimeValue getCar() { return car; }
  public RuntimeValue getCdr() { return cdr; }
  public void setCar(RuntimeValue car) { this.car = car; }
  public void setCdr(RuntimeValue cdr) { this.cdr = cdr; }
  public SourceInfo getSourceInfo() { return sourceInfo; }

  @Override
  public boolean eq(RuntimeValue other)
  { return this == other;
  }

  @Override
  public boolean eqv(RuntimeValue other) { return eq(other); }
  @Override
  public boolean equal(RuntimeValue other)
  { return other instanceof ConsValue
        && car.equal(((ConsValue) other).getCar())
        && cdr.equal(((ConsValue) other).getCdr());
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("ByteCode for " + ConsValue.class.getName()));
    method.addInstruction(new NewObjectInstruction(ConsValue.class));
    method.addInstruction(new DupInstruction());
    car.generateByteCode(mainClass, outputClass, method);
    cdr.generateByteCode(mainClass, outputClass, method);
    method.addInstruction(new NonVirtualCallInstruction(voidType, ConsValue.class.getName().replace('.', '/') + "/<init>", runtimeValueType, runtimeValueType));
  }

  @Override
  public String toString()
  { return "(" + car.toString() + " . " + cdr.toString() + ")";
  }

  @Override
  public Object toJavaValue()
  { RuntimeValue list = this;
    List<Object> returnValue = new ArrayList<>();
    while (list instanceof ConsValue)
    { returnValue.add(((ConsValue) list).getCar().toJavaValue());
      list = ((ConsValue) list).getCdr();
    }
    if (list instanceof NullValue)
    { // Proper list
      return returnValue;
    } else
    { // Not sure how to represent improper lists, so just returning this
      return this;
    }
  }

  @Override
  public boolean mutable()
  { return true;
  }
}
