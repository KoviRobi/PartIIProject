package rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.astMacroMatchVisitor.ASTMatchVisitor;

import java.util.Collection;
import java.util.ArrayList;

public interface ASTMatchVisitorReturnVisitor<T>
{ public T visit(Substitution substitution);
  public T visit(MoreASTMatchVisitors visitors);
}