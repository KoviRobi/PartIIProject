package rmk35.partIIProject.frontend.AST;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.backend.statements.Statement;

// ToDo: may be useful to actually do labels
public class SchemeLabelReference implements AST
{ String label;
  
  public SchemeLabelReference(String label)
  { this.label = label;
  }

  public String toString()
  { return label;
  }

  @Override
  public Statement accept(ASTVisitor visitor)
  { return visitor.visit(this);
  }
}
