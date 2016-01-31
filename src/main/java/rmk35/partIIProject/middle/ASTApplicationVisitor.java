package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;
import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;

import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.ApplicationStatement;

import java.util.List;
import java.util.ArrayList;

import lombok.Value;

@Value
public class ASTApplicationVisitor extends ASTVisitor<Statement>
{ Environment environment;
  AST arguments;

  public ASTApplicationVisitor(Environment environment, AST arguments)
  { this.environment = environment;
    this.arguments = arguments;
  }

  @Override
  public Statement visit(SchemeCons consCell) throws SyntaxErrorException
  { return new ApplicationStatement(consCell.accept(new ASTConvertVisitor(environment)),
      arguments.accept(new ASTListFoldVisitor<List<Statement>>(new ArrayList<>(),
        (list, ast) -> { list.add(ast.accept(new ASTConvertVisitor(environment))); return list; } )));
  }

  @Override
  public Statement visit(SchemeNil nil)
  { throw new SyntaxErrorException("Don't know how to apply nil", nil.file(), nil.line(), nil.character());
  }

  @Override
  public Statement visit(SchemeIdentifier identifier) throws SyntaxErrorException
  { return environment.lookUp(identifier.getData()).applicate(environment, identifier, arguments);
  }

  @Override
  public Statement visit(SchemeLiteral object) throws SyntaxErrorException
  { throw new SyntaxErrorException("Don't know how to apply a value as an operand", object.file(), object.line(), object.character());
  }

  @Override
  public Statement visit(SchemeLabelReference reference) throws SyntaxErrorException
  { throw new SyntaxErrorException("Don't know how to apply a label reference", reference.file(), reference.line(), reference.character());
  }

  @Override
  public Statement visit(SchemeLabelledData data) throws SyntaxErrorException
  { throw new SyntaxErrorException("Don't know how to handle non-literal labelled data", data.file(), data.line(), data.character());
  }
}
