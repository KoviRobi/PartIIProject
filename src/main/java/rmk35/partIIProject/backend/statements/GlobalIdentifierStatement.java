package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.RuntimeValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
import rmk35.partIIProject.backend.instructions.GetStaticInstruction;
import rmk35.partIIProject.backend.instructions.PutStaticInstruction;
import rmk35.partIIProject.backend.instructions.types.ObjectType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class GlobalIdentifierStatement extends IdentifierStatement
{ String name;
  private static final ObjectType type = new ObjectType(RuntimeValue.class);

  public GlobalIdentifierStatement(String name)
  { this.name = name;
  }

  @Override
  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("GlobalIdentifierStatement Get"));
    // Note getMainClass, whereas for ClosureIdentifier we have getName
    output.addToPrimaryMethod(new GetStaticInstruction(type, output.getMainClass() + "/" + name));
  }

  @Override
  public void generateSetOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("GlobalIdentifierStatement Set"));
    output.ensureFieldExists("private static", name, type);
    // Note getMainClass, whereas for ClosureIdentifier we have getName
    output.addToPrimaryMethod(new PutStaticInstruction(type, output.getMainClass() + "/" + name));
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
