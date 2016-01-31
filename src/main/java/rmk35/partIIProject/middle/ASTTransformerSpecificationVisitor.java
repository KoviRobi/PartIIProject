package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.frontend.AST.SchemeLiteral;
import rmk35.partIIProject.frontend.AST.SchemeCons;
import rmk35.partIIProject.frontend.AST.SchemeNil;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.bindings.SyntaxBinding;
import rmk35.partIIProject.middle.bindings.SyntaxRulesBinding;
import rmk35.partIIProject.middle.bindings.EllipsisBinding;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTPairMapVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import lombok.Value;

/** Syntax specification is a keyword, transformer specification, see r7rs, 7.1.3, page 64
 */
@Value
public class ASTTransformerSpecificationVisitor extends ASTVisitor<SyntaxBinding>
{ Environment environment;

  public ASTTransformerSpecificationVisitor(Environment environment)
  { this.environment = environment;
  }

  @Override
  public SyntaxBinding visit(SchemeCons consCell)
  { String syntaxRules = consCell.car().accept(new ASTExpectIdentifierVisitor()).getData();
    if (! (environment.lookUp(syntaxRules) instanceof SyntaxRulesBinding))
    { throw new SyntaxErrorException("I was expecting \"syntax-rules\" maybe it has been rebound?", consCell.file(), consCell.line(), consCell.character());
    }
    Environment ellipsisEnvironment = new Environment(environment, /* subEnvironment */ false);
    SchemeCons second = consCell.cdr().accept(new ASTExpectConsVisitor());
    AST literalsAST;
    if (second.car() instanceof SchemeIdentifier)
    { environment.addBinding(((SchemeIdentifier) second.car()).getData(), new EllipsisBinding());
      literalsAST = second.cdr();
    } else
    { environment.addBinding("...", new EllipsisBinding());
      literalsAST = second;
    }
    SchemeCons literalsCell = literalsAST.accept(new ASTExpectConsVisitor());
    List<String> literals = literalsCell.car().accept(new ASTListFoldVisitor<>(new ArrayList<>(),
      (list, ast) -> { list.add(ast.accept(new ASTExpectIdentifierVisitor()).getData()); return list; } ));
    List<Pair<AST, AST>> patternAndTemplate = literalsCell.cdr().accept(new ASTListFoldVisitor<>(new ArrayList<>(),
      (list, current) -> { list.add(current.accept(new ASTPairMapVisitor<>(x -> x, y -> y))); return list; } ));
    return new SyntaxBinding(ellipsisEnvironment, literals, patternAndTemplate);
  }

  @Override
  public SyntaxBinding visit(SchemeNil nil)
  { throw new SyntaxErrorException("List is too short for transformer specification", nil.file(), nil.line(), nil.character());
  }

  @Override
  public SyntaxBinding visit(SchemeIdentifier identifier)
  { throw new SyntaxErrorException("I was expecting a proper list", identifier.file(), identifier.line(), identifier.character());
  }

  @Override
  public SyntaxBinding visit(SchemeLiteral object)
  { throw new SyntaxErrorException("I was expecting a proper list", object.file(), object.line(), object.character());
  }

  @Override
  public SyntaxBinding visit(SchemeLabelReference reference)
  { throw new SyntaxErrorException("I was expecting a proper list", reference.file(), reference.line(), reference.character());
  }

  @Override
  public SyntaxBinding visit(SchemeLabelledData data)
  { throw new SyntaxErrorException("I was expecting a proper list", data.file(), data.line(), data.character());
  }
}
