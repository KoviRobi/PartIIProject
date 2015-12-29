package rmk35.partIIProject.middle;

import rmk35.partIIProject.backend.statements.Statement;

public interface AST
{ Statement accept(ASTVisitor visitor);
}