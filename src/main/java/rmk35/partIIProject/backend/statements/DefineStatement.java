package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.NullConstantInstruction;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class DefineStatement extends Statement
{ IdentifierStatement variable;
  Statement value;

  public DefineStatement(IdentifierStatement variable, Statement value)
  { this.variable = variable;
    this.value = value;
  }

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("SetStatement"));
    if (value == null)
    { method.addInstruction(new NullConstantInstruction());
    } else
    { value.generateOutput(mainClass, outputClass, method);
    }
    variable.ensureExistence(mainClass, outputClass, method);
    variable.generateSetOutput(mainClass, outputClass, method);
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
