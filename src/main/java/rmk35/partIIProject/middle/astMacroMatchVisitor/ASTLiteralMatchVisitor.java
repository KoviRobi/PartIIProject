package rmk35.partIIProject.middle.astMacroMatchVisitor;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
// Literals
import rmk35.partIIProject.frontend.AST.SchemeBoolean;
import rmk35.partIIProject.frontend.AST.SchemeCharacter;
import rmk35.partIIProject.frontend.AST.SchemeNumber;
import rmk35.partIIProject.frontend.AST.SchemeString;

import rmk35.partIIProject.middle.AST;

import java.util.Map;
import java.util.Hashtable;

// TODO1 could get rid of the instanceof
public class ASTLiteralMatchVisitor extends ASTNoMatchVisitor
{ SchemeLiteral storedObject;

  public ASTLiteralMatchVisitor(SchemeLiteral object)
  { this.storedObject = object;
  }

  @Override
  public Map<String, AST> visit(SchemeLiteral object)
  { throw new UnsupportedOperationException("Don't know how to match: " + object);
  }

  @Override
  public Map<String, AST> visit(SchemeBoolean booleanValue)
  { if (storedObject instanceof SchemeBoolean && ((SchemeBoolean) storedObject).getData() == booleanValue.getData())
    { return new Hashtable<>();
    } else
    { return null;
    }
  }

  // ToDo: byteVectors

  @Override
  public Map<String, AST> visit(SchemeCharacter character)
  { if (storedObject instanceof SchemeCharacter && ((SchemeCharacter) storedObject).getData() == character.getData())
    { return new Hashtable<>();
    } else
    { return null;
    }
  }

  @Override
  public Map<String, AST> visit(SchemeNumber number)
  { if (storedObject instanceof SchemeNumber && ((SchemeNumber) storedObject).getData().equals(number.getData()))
    { return new Hashtable<>();
    } else
    { return null;
    }
  }

  @Override
  public Map<String, AST> visit(SchemeString string)
  { if (storedObject instanceof SchemeString && ((SchemeString) storedObject).getData().equals(string.getData()))
    { return new Hashtable<>();
    } else
    { return null;
    }
  }
}
