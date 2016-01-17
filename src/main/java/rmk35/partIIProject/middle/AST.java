package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.backend.statements.Statement;

public interface AST
{ <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException;
  String file();
  long line();
  long character();
}