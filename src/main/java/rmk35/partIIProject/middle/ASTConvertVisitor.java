package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
import rmk35.partIIProject.frontend.AST.SchemeList;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
// Literals
import rmk35.partIIProject.frontend.AST.SchemeNumber;

import rmk35.partIIProject.middle.bindings.Binding;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.RuntimeValueStatement;
import rmk35.partIIProject.backend.runtimeValues.NumberValue;

import lombok.Value;

import java.util.List;

/* This is the main visitor, it converts the frontend's AST to the backend's Statement */

@Value
public class ASTConvertVisitor extends ASTVisitor<Statement>
{ Environment environment; /* STATE */

  public ASTConvertVisitor(Environment environment)
  { this.environment =  environment;
  }

  @Override
  public Statement visit(SchemeList list)
  { List<AST> innerList = list.getData();

    if (innerList.size() == 0)
    { throw new SyntaxErrorException("Application without operator or operands", list.file(), list.line(), list.character());
    } else
    { return innerList.get(0)
                      .accept(new ASTApplicationVisitor(environment, list.getData()));
    }
  }

  @Override
  public Statement visit(SchemeIdentifier identifier)
  { return environment.lookUpAsStatement(identifier.getData());
  }

  @Override
  public Statement visit(SchemeLiteral object)
  { throw new UnsupportedOperationException();
  }

  @Override
  public Statement visit(SchemeNumber number)
  { return new RuntimeValueStatement(number.getData(), NumberValue.class, new String[] {"I"});
  }

  @Override
  public Statement visit(SchemeLabelReference reference)
  { throw new SyntaxErrorException("Unexpected label reference", reference.file(), reference.line(), reference.character());
  }

  @Override
  public Statement visit(SchemeLabelledData data)
  { throw new SyntaxErrorException("Don't know how to handle labelled data in non-literal position", data.file(), data.line(), data.character());
  }
}
