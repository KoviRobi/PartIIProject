package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;

import java.util.List;
import java.util.Arrays;

import lombok.ToString;

@ToString
public class SequenceStatement extends Statement
{ List<Statement> statements;

  public SequenceStatement(List<Statement> statements)
  { this.statements = statements;
  }

  public SequenceStatement(Statement... statements)
  { this.statements = Arrays.asList(statements);
  }

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { if (! statements.isEmpty())
    { method.addInstruction(new CommentPseudoInstruction("SequenceStatement"));
      for (Statement statement : statements)
      { statement.generateOutput(mainClass, outputClass, method);
      }
    }
  }
}
