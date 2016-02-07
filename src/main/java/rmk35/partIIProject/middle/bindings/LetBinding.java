package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;
import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;
import rmk35.partIIProject.middle.ASTBindingSpecificationVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;

import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.IdentifierStatement;
import rmk35.partIIProject.backend.statements.BeginStatement;
import rmk35.partIIProject.backend.statements.DefineStatement;

import java.util.List;
import java.util.ArrayList;

import lombok.Value;

@Value
public class LetBinding implements Binding
{ @Override
  public Statement toStatement(String file, long line, long character)
  { throw new SyntaxErrorException("Don't know how to use a syntactic variable in a run time context", file, line, character);
  }

  @Override
  public Statement applicate(Environment environment, AST operator, AST operands)
  { SchemeCons first = operands.accept(new ASTExpectConsVisitor());
    //  Copy environment for lexical effect
    Environment letEnvironment = new Environment(environment, false);

    // ToDo: named let, could optimise to use goto
    List<Pair<String, Statement>> bindingSpecifications = first.car().accept(new ASTListFoldVisitor<>(new ArrayList<>(),
      (List<Pair<String, Statement>> list, AST ast) -> { list.add(ast.accept(new ASTBindingSpecificationVisitor(letEnvironment))); return list; } ));

    List<Statement> bindingStatements = new ArrayList<>();
    for (Pair<String, Statement> binding : bindingSpecifications)
    { letEnvironment.addLocalVariable(binding.getFirst());
      IdentifierStatement local = (IdentifierStatement) letEnvironment.lookUpAsStatement(binding.getFirst(), operator.file(), operator.line(), operator.character());
      bindingStatements.add(new DefineStatement(local, binding.getSecond()));
    }

    // Implicit begin (superset of the standard but useful)
    List<Statement> body = first.cdr().accept
      (new ASTListFoldVisitor<List<Statement>>(new ArrayList<>(),
        (list, ast) -> { list.add(ast.accept(new ASTConvertVisitor(letEnvironment))); return list; } ));
    if (body.isEmpty())
    { throw new SyntaxErrorException("Empty lambda body", operator.file(), operator.line(), operator.character());
    }
    bindingStatements.addAll(body);
    return new BeginStatement(bindingStatements);
  }

  @Override
  public boolean shouldSaveToClosure()
  { return false;
  }

  @Override
  public Binding subEnvironment()
  { return this;
  }
}
