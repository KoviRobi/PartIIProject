package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeAbbreviation;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
import rmk35.partIIProject.frontend.AST.SchemeList;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLiteral;

import rmk35.partIIProject.backend.statements.Statement;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ASTLambdaFormalsVisitor implements ASTVisitor<List<String>>
{ private List<String> returnList; // NOTE: returnList get mutated when visiting a list, this is quite subtle and is bad design

  public ASTLambdaFormalsVisitor()
  { returnList = new ArrayList<>(); // NOTE: returnList get mutated when visiting a list, this is quite subtle and is bad design
  }

  public List<String> visit(SchemeList list)
  { List<AST> underlyingList = list.getData();
    underlyingList.parallelStream().forEach(ast -> ast.accept(this));
    return returnList; // NOTE: returnList get mutated when visiting a list, this is quite subtle and is bad design
  }

  public List<String> visit(SchemeIdentifier identifier)
  { returnList.add(identifier.getData()); // NOTE: returnList get mutated when visiting a list, this is quite subtle and is bad design
    return returnList;
  }

  public List<String> visit(SchemeLabelReference reference)
  { throw new SyntaxErrorException("Don't know how to handle label reference as formal parameter.", reference.file(), reference.line(), reference.character());
  }

  public List<String> visit(SchemeLiteral object)
  { throw new SyntaxErrorException("Don't know how to handle an object as formal parameter.", object.file(), object.line(), object.character());
  }
}