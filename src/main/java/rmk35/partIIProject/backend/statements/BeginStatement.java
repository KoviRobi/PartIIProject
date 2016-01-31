package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.InnerClass;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.NullConstantInstruction;
import rmk35.partIIProject.backend.instructions.PopInstruction;

import java.util.Collection;
import java.util.TreeSet;
import java.util.List;

import lombok.ToString;

@ToString
public class BeginStatement extends Statement
{ List<Statement> statements;

  public BeginStatement(List<Statement> statements)
  { this.statements = statements;
  }

  @SuppressWarnings("deprecation")
  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("BeginStatement"));
    method.addInstruction(new NullConstantInstruction());
    for (Statement statement : statements)
    { method.addInstruction(new PopInstruction());
      statement.generateOutput(mainClass, outputClass, method);
    }
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> returnValue = new TreeSet<>();
    for (Statement statement : statements)
    { returnValue.addAll(statement.getFreeIdentifiers());
    }
    return returnValue;
  }
}
