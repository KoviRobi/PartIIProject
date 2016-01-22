package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;

import rmk35.partIIProject.backend.statements.Statement;

import java.util.function.BiFunction;

import lombok.Data;

@Data
public class ASTListFoldVisitor<T> extends ASTVisitor<T>
{ T foldValue; /* STATE */
  BiFunction<T, AST, T> foldFunction;

  public ASTListFoldVisitor(T initialValue, BiFunction<T, AST, T> foldFunction)
  { this.foldValue = initialValue;
    this.foldFunction = foldFunction;
  }

  @Override
  public T visit(SchemeCons consCell)
  { foldValue = foldFunction.apply(foldValue, consCell.car());
    return consCell.cdr().accept(this);
  }

  @Override
  public T visit(SchemeNil nil)
  { return foldValue;
  }

  @Override
  public T visit(SchemeIdentifier identifier)
  { throw new SyntaxErrorException("I was expecting a proper list", identifier.file(), identifier.line(), identifier.character());
  }

  @Override
  public T visit(SchemeLiteral object)
  { throw new SyntaxErrorException("I was expecting a proper list", object.file(), object.line(), object.character());
  }

  @Override
  public T visit(SchemeLabelReference reference)
  { throw new SyntaxErrorException("I was expecting a proper list", reference.file(), reference.line(), reference.character());
  }

  @Override
  public T visit(SchemeLabelledData data)
  { throw new SyntaxErrorException("I was expecting a proper list", data.file(), data.line(), data.character());
  }
}
