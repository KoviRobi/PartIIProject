package rmk35.partIIProject.middle;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.frontend.AST.SchemeObject;
import rmk35.partIIProject.frontend.AST.SchemeList;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;

import rmk35.partIIProject.middle.bindings.Binding;

import rmk35.partIIProject.backend.statements.Statement;

import lombok.Value;

import java.util.List;

/* This is the main visitor, it converts the frontend's AST to the backend's Statement */

@Value
public class ASTConvertVisitor implements ASTVisitor
{ Environment environment;

  @Override
  public Statement visit(SchemeList list)
  { List<AST> innerList = list.getData();

    // FIXME: This is a user exception, not an internal one
    if (innerList.size() == 0)
    { throw new InternalCompilerException("Application without operator or operands");
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
  public Statement visit(SchemeObject object)
  { throw new UnsupportedOperationException();
  }

  @Override
  public Statement visit(SchemeLabelReference reference)
  { throw new UnsupportedOperationException();
    // This may actually be an error, if we convert SchemeLabelReferences to actual looping references
  }
}
