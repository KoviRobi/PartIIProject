package rmk35.partIIProject.middle;

import rmk35.partIIProject.frontend.AST.SchemeAbbreviation;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
import rmk35.partIIProject.frontend.AST.SchemeList;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeObject;

import rmk35.partIIProject.backend.statements.Statement;

public interface ASTVisitor
{ Statement visit(SchemeAbbreviation abbreviation);
  Statement visit(SchemeList list);
  Statement visit(SchemeIdentifier identifier);
  Statement visit(SchemeLabelReference reference);
  Statement visit(SchemeObject object);
}
