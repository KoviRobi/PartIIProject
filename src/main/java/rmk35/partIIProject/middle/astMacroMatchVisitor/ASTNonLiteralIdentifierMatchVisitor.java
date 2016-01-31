package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;

import rmk35.partIIProject.middle.AST;

import java.util.Map;
import java.util.Hashtable;

public class ASTNonLiteralIdentifierMatchVisitor extends ASTMatchVisitor
{ String variableName;

  public ASTNonLiteralIdentifierMatchVisitor(String variableName)
  { this.variableName = variableName;
  }

  @Override
  public Map<String, AST> visit(SchemeCons consCell)
  { Map<String, AST> returnValue = new Hashtable<>();
    returnValue.put(variableName, consCell);
    return returnValue;
  }

  @Override
  public Map<String, AST> visit(SchemeNil nil)
  { Map<String, AST> returnValue = new Hashtable<>();
    returnValue.put(variableName, nil);
    return returnValue;
  }

  @Override
  public Map<String, AST> visit(SchemeIdentifier identifier)
  { Map<String, AST> returnValue = new Hashtable<>();
    returnValue.put(variableName, identifier);
    return returnValue;
  }

  @Override
  public Map<String, AST> visit(SchemeLiteral object)
  { Map<String, AST> returnValue = new Hashtable<>();
    returnValue.put(variableName, object);
    return returnValue;
  }

  @Override
  public Map<String, AST> visit(SchemeLabelReference reference)
  { Map<String, AST> returnValue = new Hashtable<>();
    returnValue.put(variableName, reference);
    return returnValue;
  }

  @Override
  public Map<String, AST> visit(SchemeLabelledData data)
  { Map<String, AST> returnValue = new Hashtable<>();
    returnValue.put(variableName, data);
    return returnValue;
  }
}
