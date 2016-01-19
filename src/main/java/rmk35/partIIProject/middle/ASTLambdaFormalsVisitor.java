package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeAbbreviation;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLiteral;

import rmk35.partIIProject.backend.statements.Statement;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/* Note: this is not using the functional approach of walk/merge but rather
   the mutation approach
 */
public class ASTLambdaFormalsVisitor extends ASTVisitor<List<String>>
{ private List<String> returnList; /* STATE */

  public ASTLambdaFormalsVisitor()
  { returnList = new ArrayList<>(); // NOTE: returnList get mutated when visiting a list
  }

  public List<String> visit(SchemeCons consCell)
  { consCell.car().accept(this);
    consCell.cdr().accept(this);
    return returnList;
  }

  @Override
  public List<String> visit(SchemeNil nil)
  { return returnList;
  }

  public List<String> visit(SchemeIdentifier identifier)
  { returnList.add(identifier.getData()); // NOTE: returnList get mutated when visiting a list
    return returnList;
  }

  public List<String> visit(SchemeLabelReference reference)
  { throw new SyntaxErrorException("Don't know how to handle label reference as formal parameter.", reference.file(), reference.line(), reference.character());
  }

  public List<String> visit(SchemeLabelledData data)
  { throw new SyntaxErrorException("Don't know how to handle labelled data as formal parameter.", data.file(), data.line(), data.character());
  }

  public List<String> visit(SchemeLiteral object)
  { throw new SyntaxErrorException("Don't know how to handle an object as formal parameter.", object.file(), object.line(), object.character());
  }
}