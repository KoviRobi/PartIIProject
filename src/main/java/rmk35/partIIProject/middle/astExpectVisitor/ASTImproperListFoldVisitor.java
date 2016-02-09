package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.SelfquotingValue;

import rmk35.partIIProject.middle.ASTVisitor;

import java.util.function.BiFunction;

import lombok.Data;

@Data
public class ASTImproperListFoldVisitor<T> extends ASTVisitor<T>
{ T foldValue; /* STATE */
  BiFunction<T, RuntimeValue, T> foldFunction;

  public ASTImproperListFoldVisitor(T initialValue, BiFunction<T, RuntimeValue, T> foldFunction)
  { this.foldValue = initialValue;
    this.foldFunction = foldFunction;
  }

  @Override
  public T visit(ConsValue consCell)
  { foldValue = foldFunction.apply(foldValue, consCell.getCar());
    return consCell.getCdr().accept(this);
  }

  @Override
  public T visit(NullValue nil) // Treating this as a case of a proper list
  { return foldValue;
  }

  @Override
  public T visit(IdentifierValue identifier)
  { return foldFunction.apply(foldValue, identifier);
  }

  @Override
  public T visit(SelfquotingValue object)
  { return foldFunction.apply(foldValue, object);
  }
}
