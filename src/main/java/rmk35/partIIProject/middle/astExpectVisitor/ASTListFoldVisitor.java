package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.NullValue;

import java.util.function.BiFunction;

import lombok.Data;

@Data
public class ASTListFoldVisitor<T> extends ASTUnexpectedVisitor<T>
{ T foldValue; /* STATE */
  BiFunction<T, RuntimeValue, T> foldFunction;

  public ASTListFoldVisitor(T initialValue, BiFunction<T, RuntimeValue, T> foldFunction)
  { this.foldValue = initialValue;
    this.foldFunction = foldFunction;
  }

  @Override
  public T visit(ConsValue consCell)
  { foldValue = foldFunction.apply(foldValue, consCell.getCar());
    return consCell.getCdr().accept(this);
  }

  @Override
  public T visit(NullValue nil)
  { return foldValue;
  }
}
