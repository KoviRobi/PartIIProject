package rmk35.partIIProject.middle;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.bindings.SyntaxBinding;
import rmk35.partIIProject.middle.bindings.SyntaxRulesBinding;
import rmk35.partIIProject.middle.bindings.EllipsisBinding;
import rmk35.partIIProject.middle.astExpectVisitor.ASTUnexpectedVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTPairMapVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;

import rmk35.partIIProject.backend.MainClass;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import lombok.Value;

/** Syntax specification is a keyword, transformer specification, see r7rs, 7.1.3, page 64
 */
@Value
public class ASTTransformerSpecificationVisitor extends ASTUnexpectedVisitor<SyntaxBinding>
{ EnvironmentValue environment;
  MainClass mainClass;

  public ASTTransformerSpecificationVisitor(EnvironmentValue environment, MainClass mainClass)
  { this.environment = environment;
     this.mainClass = mainClass;
  }

  @Override
  public SyntaxBinding visit(ConsValue consCell)
  { String syntaxRules = consCell.getCar().accept(new ASTExpectIdentifierVisitor()).getValue();
    if (! (environment.getOrGlobal(mainClass, syntaxRules) instanceof SyntaxRulesBinding))
    { throw new SyntaxErrorException("I was expecting \"syntax-rules\", maybe it has been rebound?", consCell.getSourceInfo());
    }
    String ellipsisString;
    ConsValue second = consCell.getCdr().accept(new ASTExpectConsVisitor());
    RuntimeValue literalsAST;
    if (second.getCar() instanceof IdentifierValue)
    { ellipsisString = ((IdentifierValue) second.getCar()).getValue();
      literalsAST = second.getCdr();
    } else
    { ellipsisString = "...";
      literalsAST = second;
    }
    ConsValue literalsCell = literalsAST.accept(new ASTExpectConsVisitor());
    List<String> literals = literalsCell.getCar().accept(new ASTListFoldVisitor<>(new ArrayList<>(),
      (list, ast) -> { list.add(ast.accept(new ASTExpectIdentifierVisitor()).getValue()); return list; } ));
    List<Pair<RuntimeValue, RuntimeValue>> patternAndTemplate = literalsCell.getCdr().accept(new ASTListFoldVisitor<>(new ArrayList<>(),
      (list, current) -> { list.add(current.accept(new ASTPairMapVisitor<>(x -> x, y -> y))); return list; } ));
    SyntaxBinding returnValue = new SyntaxBinding(environment, literals, patternAndTemplate, ellipsisString);
    return returnValue;
  }
}
