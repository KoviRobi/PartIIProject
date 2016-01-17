package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeLabelledData;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
import rmk35.partIIProject.frontend.AST.SchemeList;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLiteral;
// Literals
import rmk35.partIIProject.frontend.AST.SchemeAbbreviation;
import rmk35.partIIProject.frontend.AST.SchemeBoolean;
import rmk35.partIIProject.frontend.AST.SchemeBytevector;
import rmk35.partIIProject.frontend.AST.SchemeCharacter;
import rmk35.partIIProject.frontend.AST.SchemeNumber;
import rmk35.partIIProject.frontend.AST.SchemeString;
import rmk35.partIIProject.frontend.AST.SchemeVector;

import rmk35.partIIProject.backend.statements.Statement;

public abstract class ASTVisitor<T>
{ public abstract T visit(SchemeList list) throws SyntaxErrorException;
  public abstract T visit(SchemeIdentifier identifier) throws SyntaxErrorException;
  public abstract T visit(SchemeLabelledData reference) throws SyntaxErrorException;
  public abstract T visit(SchemeLabelReference reference) throws SyntaxErrorException;
  public abstract T visit(SchemeLiteral object) throws SyntaxErrorException;

  // SchemeObject subtypes, in case we want to specialise
  public T visit(SchemeAbbreviation abbreviation) throws SyntaxErrorException { return visit((SchemeLiteral)abbreviation); }
  public T visit(SchemeBoolean booln) throws SyntaxErrorException { return visit((SchemeLiteral)booln); }
  public T visit(SchemeBytevector bytevector) throws SyntaxErrorException { return visit((SchemeLiteral)bytevector); }
  public T visit(SchemeCharacter character) throws SyntaxErrorException { return visit((SchemeLiteral)character); }
  public T visit(SchemeNumber number) throws SyntaxErrorException { return visit((SchemeLiteral)number); }
  public T visit(SchemeString string) throws SyntaxErrorException { return visit((SchemeLiteral)string); }
  public T visit(SchemeVector vector) throws SyntaxErrorException { return visit((SchemeLiteral)vector); }
}
