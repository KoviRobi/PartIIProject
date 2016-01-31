package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.backend.statements.Statement;

import java.util.function.BiFunction;

import lombok.Data;

@Data
public class ASTImproperListFoldVisitor<T> extends ASTVisitor<T>
{ T foldValue; /* STATE */
  BiFunction<T, AST, T> foldFunction;

  public ASTImproperListFoldVisitor(T initialValue, BiFunction<T, AST, T> foldFunction)
  { this.foldValue = initialValue;
    this.foldFunction = foldFunction;
  }

  @Override
  public T visit(SchemeCons consCell)
  { foldValue = foldFunction.apply(foldValue, consCell.car());
    return consCell.cdr().accept(this);
  }

  @Override
  public T visit(SchemeNil nil) // Treating this as a case of a proper list
  { return foldValue;
  }

  @Override
  public T visit(SchemeIdentifier identifier)
  { return foldFunction.apply(foldValue, identifier);
  }

  @Override
  public T visit(SchemeLiteral object)
  { return foldFunction.apply(foldValue, object);
  }

  @Override
  public T visit(SchemeLabelReference reference)
  { return foldFunction.apply(foldValue, reference);
  }

  @Override
  public T visit(SchemeLabelledData data)
  { return foldFunction.apply(foldValue, data);
  }
}
