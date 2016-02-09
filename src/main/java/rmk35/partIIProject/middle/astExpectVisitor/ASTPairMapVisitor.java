package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;

import java.util.function.Function;

public class ASTPairMapVisitor<A, B> extends ASTUnexpectedVisitor<Pair<A, B>>
{ Function<RuntimeValue, A> firstMap;
  Function<RuntimeValue, B> secondMap;

  public ASTPairMapVisitor(Function<RuntimeValue, A> firstMap, Function<RuntimeValue, B> secondMap)
  { this.firstMap = firstMap;
    this.secondMap = secondMap;
  }

  @Override
  public Pair<A, B> visit(ConsValue consCell)
  { A first = firstMap.apply(consCell.getCar());
    ConsValue secondCell = consCell.getCdr().accept(new ASTExpectConsVisitor());
    B second = secondMap.apply(secondCell.getCar());
    secondCell.getCdr().accept(new ASTExpectNilVisitor());
    return new Pair<>(first, second);
  }
}
