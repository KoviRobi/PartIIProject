package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.middle.Environment;
import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.ASTAndEnvironment;
import rmk35.partIIProject.middle.ASTMacroTranscribeVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;

import rmk35.partIIProject.backend.statements.Statement;

import java.util.List;

import lombok.Value;

@Value
public class SyntaxBinding implements Binding
{ Environment storedEnvironment;
  List<String> literals;
  AST pattern;
  AST template;

  public SyntaxBinding(Environment storedEnvironment, List<String> literals, AST pattern, AST template)
  { this.storedEnvironment = storedEnvironment;
    this.literals = literals;
    this.pattern = pattern;
    this.template = template;
  }

  @Override
  public Statement toStatement(String file, long line, long character)
  { throw new SyntaxErrorException("Don't know how to use a syntactic variable in a run time context", file, line, character);
  }

  @Override
  public Statement applicate(Environment environment, AST arguments, String file, long line, long character)
  { ASTAndEnvironment transcribed = arguments.accept
      (new ASTMacroTranscribeVisitor(storedEnvironment, environment, pattern, template));
    return transcribed.getAst().accept(new ASTConvertVisitor(transcribed.getEnvironment()));
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
