package rmk35.partIIProject.runtime;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;

public class TrampolineValue implements RuntimeValue
{ // A trampoline is used to encapsulate a call to a LambdaValue
  LambdaValue function;
  RuntimeValue arguments;

  // Inner class defined at the bottom of the file
  private static final TrampolineVisitor trampolineVisitor = new TrampolineVisitor();

  public TrampolineValue(LambdaValue function, RuntimeValue arguments)
  { this.function = function;
    this.arguments = arguments;
  }

  public static RuntimeValue bounceHelper(RuntimeValue object)
  { if (object != null && object instanceof TrampolineValue)
    { return ((TrampolineValue) object).bounce();
    } else
    { return object;
    }
  }

  public RuntimeValue bounce()
  { // TrampolineVisitor calls tail calls, or returns normal RuntimeValues
    RuntimeValue returnValue = this;
    // While we have tail calls to perform
    while (returnValue != null && returnValue instanceof TrampolineValue)
    { returnValue = acceptHelper(returnValue);
    }
    return returnValue;
  }

  public static RuntimeValue acceptHelper(RuntimeValue value)
  { if (value == null || ! (value instanceof RuntimeValue))
    { return value; // null is the undefined value, non-RuntimeValues returned by java calls
    } else
    { return ((RuntimeValue) value).accept(trampolineVisitor);
    }
  }

  public RuntimeValue call()
  { return function.apply(arguments);
  }

  @Override
  public SourceInfo getSourceInfo() { return null; }

  public boolean equal(RuntimeValue other) { throw new InternalCompilerException("A trampoline is an internal data structure, it should not be externally visible."); }
  public boolean eqv(RuntimeValue other) { throw new InternalCompilerException("A trampoline is an internal data structure, it should not be externally visible."); }
  public boolean eq(RuntimeValue other) { throw new InternalCompilerException("A trampoline is an internal data structure, it should not be externally visible."); }
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method) { throw new InternalCompilerException("A trampoline is an internal data structure, it should not be externally visible."); }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  private static class TrampolineVisitor extends ASTVisitor<RuntimeValue>
  { @Override
    public RuntimeValue visit(TrampolineValue trampoline) { return trampoline.call(); }

    @Override
    public RuntimeValue visit(RuntimeValue value) { return value; }
  }

  @Override
  public Object toJavaValue() { throw new InternalCompilerException("A trampoline is an internal data structure, it should not be externally visible."); }

  @Override
  public boolean mutable()
  { return false;
  }
}
