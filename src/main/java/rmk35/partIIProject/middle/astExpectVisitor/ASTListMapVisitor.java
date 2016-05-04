package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.runtime.RuntimeValue;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;

public class ASTListMapVisitor<T> extends ASTListFoldVisitor<List<T>>
{ public ASTListMapVisitor(Function<RuntimeValue, T> function)
   { super(new ArrayList<T>(), (list, ast) ->
       { list.add(function.apply(ast));
         return list;
       });
  }

  public ASTListMapVisitor(ASTVisitor<T>visitor)
   { super(new ArrayList<T>(), (list, ast) ->
       { list.add(ast.accept(visitor));
         return list;
       });
  }
}
