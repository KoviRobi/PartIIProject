package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeAbbreviation;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
import rmk35.partIIProject.frontend.AST.SchemeList;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeObject;

import rmk35.partIIProject.backend.statements.Statement;

public interface ASTVisitor<T>
{ T visit(SchemeList list) throws SyntaxErrorException;
  T visit(SchemeIdentifier identifier) throws SyntaxErrorException;
  T visit(SchemeLabelReference reference) throws SyntaxErrorException;
  T visit(SchemeObject object) throws SyntaxErrorException;
}
