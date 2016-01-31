package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.frontend.AST.SchemeCons;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.astMacroMatchVisitor.ASTCompilePatternVisitor;
import rmk35.partIIProject.middle.astMacroMatchVisitor.ASTMatchVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;

import rmk35.partIIProject.backend.statements.Statement;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Hashtable;

import lombok.Value;

@Value
public class SyntaxBinding implements Binding
{ Collection<String> literals;
  List<Pair<ASTMatchVisitor, AST>> patternsAndTemplates;

  public SyntaxBinding(Environment storedEnvironment, Collection<String> literals, List<Pair<AST, AST>> patternsAndTemplates)
  { this.literals = literals;
    this.patternsAndTemplates = new ArrayList<>(patternsAndTemplates.size());
    for (Pair<AST, AST> pair : patternsAndTemplates)
    { this.patternsAndTemplates.add(new Pair<>(pair.getFirst().accept(new ASTCompilePatternVisitor(literals, storedEnvironment)), pair.getSecond()));
    }
  }

  @Override
  public Statement toStatement(String file, long line, long character)
  { throw new SyntaxErrorException("Don't know how to use a syntactic variable in a run time context", file, line, character);
  }

  @Override
  public Statement applicate(Environment useEnvironment, AST operator, AST operands)
  { //Substitution substitution = arguments.accept
    //  (new ASTMacroMatchVisitor(storedEnvironment, useEnvironment, literals, pattern));
    //Pair<AST, Environment> transcribed = arguments.accept
    //  (new ASTMacroRewriteVisitor(storedEnvironment, substitution, literals, template));
    //return transcribed.getFirst().accept(new ASTConvertVisitor(transcribed.getSecond()));
    for (Pair<ASTMatchVisitor, AST> pair : patternsAndTemplates)
    { pair.getFirst().setUseEnvironment(useEnvironment);
      Map<String, AST> substitution = (new SchemeCons(operator, operands, operator.file(), operator.line(), operator.character())).accept(pair.getFirst());
    }
    //applicationVisitor.setUseEnvironment(useEnvironment);
    //return null; // NEXT 4: transcribe macro, then evaluate it
    throw new SyntaxErrorException("Incorrect macro use", operator.file(), operator.line(), operator.character());
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
