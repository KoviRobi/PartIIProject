package rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.astMacroMatchVisitor.ASTMatchVisitor;

import java.util.Collection;
import java.util.ArrayList;

public class MoreASTMatchVisitors implements ASTMatchVisitorReturn
{ Collection<ASTMatchVisitor> astMatchVisitors;

  public MoreASTMatchVisitors()
  { this.astMatchVisitors = new ArrayList<>();
  }

  public <T> T accept(ASTMatchVisitorReturnVisitor<T> visitor)
  { return visitor.visit(this);
  }

  public void add(ASTMatchVisitor visitor)
  { astMatchVisitors.add(visitor);
  }

  public Collection<ASTMatchVisitor> getVisitors()
  { return astMatchVisitors;
  }
}