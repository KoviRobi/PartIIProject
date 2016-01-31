package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.frontend.AST.SchemeNil;

import rmk35.partIIProject.middle.AST;

import java.util.Map;
import java.util.Hashtable;

public class ASTNilMatchVisitor extends ASTNoMatchVisitor
{ @Override
  public Map<String, AST> visit(SchemeNil nil)
  { return new Hashtable<>();
  }
}
