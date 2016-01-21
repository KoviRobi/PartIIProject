package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.NullConstantInstruction;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class SetStatement extends Statement
{ IdentifierStatement variable;
  Statement value;

  public SetStatement(IdentifierStatement variable, Statement value)
  { this.variable = variable;
    this.value = value;
  }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("SetStatement"));
    if (value == null)
    { output.addToPrimaryMethod(new NullConstantInstruction());
    } else
    { value.generateOutput(output);
    }
    variable.generateSetOutput(output);
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
