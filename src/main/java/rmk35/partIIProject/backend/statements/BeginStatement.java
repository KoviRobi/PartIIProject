package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.TrampolineValue;
import rmk35.partIIProject.runtime.UnspecifiedValue;

import rmk35.partIIProject.backend.InnerClass;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import rmk35.partIIProject.backend.instructions.PopInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;

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

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("BeginStatement"));
    new RuntimeValueStatement(new UnspecifiedValue()).generateOutput(mainClass, outputClass, method);
    for (Statement statement : statements)
    { method.addInstruction(new StaticCallInstruction(runtimeValueType, TrampolineValue.class.getName().replace('.', '/') + "/bounceHelper", runtimeValueType));
      method.addInstruction(new PopInstruction());
      statement.generateOutput(mainClass, outputClass, method);
    }
  }
}
