package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;
import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;
import rmk35.partIIProject.middle.ASTSyntaxSpecificationVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;

import rmk35.partIIProject.backend.statements.Statement;

import java.util.List;
import java.util.ArrayList;

import lombok.Value;

@Value
public class LetSyntaxBinding implements Binding
{ @Override
  public Statement toStatement(String file, long line, long character)
  { throw new SyntaxErrorException("Don't know how to use a syntactic variable in a run time context", file, line, character);
  }
  
  @Override
  public Statement applicate(Environment environment, AST operator, AST operands)
  { SchemeCons first = operands.accept(new ASTExpectConsVisitor());
    //  Copy environment for lexical effect
    Environment letEnvironment = new Environment(environment, false);

    // XXX To think about: letrec-syntax in particular this and looking up a variable in the environment to see if it has changed

    List<Pair<String, SyntaxBinding>> macros = first.car().accept(new ASTListFoldVisitor<>(new ArrayList<>(),
      (List<Pair<String, SyntaxBinding>> list, AST ast) -> { list.add(ast.accept(new ASTSyntaxSpecificationVisitor(letEnvironment))); return list; } ));

    for (Pair<String, SyntaxBinding> macro : macros)
    { letEnvironment.addBinding(macro.getFirst(), macro.getSecond());
    }

    return first.cdr().accept(new ASTConvertVisitor(letEnvironment));
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
