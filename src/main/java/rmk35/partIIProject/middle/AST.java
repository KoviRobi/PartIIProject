package rmk35.partIIProject.middle;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.SyntaxErrorException;

public interface AST
{ <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException;
  SourceInfo getSourceInfo();
}