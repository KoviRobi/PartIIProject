package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.Environment;

import java.util.Map;

public class ASTNoMatchVisitor extends ASTMatchVisitor
{ @Override
  public Map<String, AST> visit(SchemeCons consCell)
  { return null;
  }

  @Override
  public Map<String, AST> visit(SchemeNil nil)
  { return null;
  }

  @Override
  public Map<String, AST> visit(SchemeIdentifier identifier)
  { return null;
  }

  @Override
  public Map<String, AST> visit(SchemeLiteral object)
  { return null;
  }

  @Override
  public Map<String, AST> visit(SchemeLabelReference reference)
  { return null;
  }

  @Override
  public Map<String, AST> visit(SchemeLabelledData data)
  { return null;
  }
}
