package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.RuntimeValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
import rmk35.partIIProject.backend.instructions.LocalStoreInstruction;
import rmk35.partIIProject.backend.instructions.types.ObjectType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class LocalIdentifierStatement extends IdentifierStatement
{ String name;
  int localIndex;
  private static final ObjectType type = new ObjectType(RuntimeValue.class);

  public LocalIdentifierStatement(String name, int localIndex)
  { this.name = name;
    this.localIndex = localIndex;
  }

  @Override
  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("LocalIdentifier Get"));
    output.addToPrimaryMethod(new LocalLoadInstruction(type, localIndex));
  }

  /* Assumes variable to set to is on top of the stack */
  @Override
  public void generateSetOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("LocalIdentifier Set"));
    output.addToPrimaryMethod(new LocalStoreInstruction(type, localIndex));
  }

  @Override
  public String getName()
  { return name;
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
