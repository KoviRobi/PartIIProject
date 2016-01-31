package rmk35.partIIProject.middle.astExpectVisitor;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;

import java.util.function.Function;

public class ASTPairMapVisitor<A, B> extends ASTUnexpectedVisitor<Pair<A, B>>
{ Function<AST, A> firstMap;
  Function<AST, B> secondMap;

  public ASTPairMapVisitor(Function<AST, A> firstMap, Function<AST, B> secondMap)
  { this.firstMap = firstMap;
    this.secondMap = secondMap;
  }

  @Override
  public Pair<A, B> visit(SchemeCons consCell)
  { A first = firstMap.apply(consCell.car());
    SchemeCons secondCell = consCell.cdr().accept(new ASTExpectConsVisitor());
    B second = secondMap.apply(secondCell.car());
    secondCell.cdr().accept(new ASTExpectNilVisitor());
    return new Pair<>(first, second);
  }
}
