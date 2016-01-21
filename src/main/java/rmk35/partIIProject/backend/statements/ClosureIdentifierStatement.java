package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.runtimeValues.RuntimeValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
import rmk35.partIIProject.backend.instructions.GetFieldInstruction;
import rmk35.partIIProject.backend.instructions.PutFieldInstruction;
import rmk35.partIIProject.backend.instructions.types.ObjectType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

/**
 * Closure variables are stored in the current object's field
 */
@ToString
public class ClosureIdentifierStatement extends IdentifierStatement
{ String name;
  private static final ObjectType type = new ObjectType(RuntimeValue.class);

  public ClosureIdentifierStatement(String name)
  { this.name = name;
  }

  @Override
  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("ClosureIdentifierStatement Get"));
    output.addToPrimaryMethod(new LocalLoadInstruction(type, 0)); // 'this', the current object
    // Note getClass, whereas for GlobalIdentifier we have getMainClass
    output.addToPrimaryMethod(new GetFieldInstruction(type, output.getName() + "/" + name));
  }

  @Override
  public void generateSetOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("ClosureIdentifierStatement Set"));
    output.ensureFieldExists("private", name, type);
    output.addToPrimaryMethod(new LocalLoadInstruction(type, 0)); // 'this', the current object
    // Note getClass, whereas for GlobalIdentifier we have getMainClass
    output.addToPrimaryMethod(new PutFieldInstruction(type, output.getName() + "/" + name));
  }

  @Override
  public String getName()
  { return name;
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> returnValue = new TreeSet<>();
    returnValue.add(getName());
    return returnValue;
  }
}
