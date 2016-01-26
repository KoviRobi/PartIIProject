package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
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
  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("ClosureIdentifierStatement Get"));
    method.addInstruction(new LocalLoadInstruction(type, 0)); // 'this', the current object
    // Note getClass, whereas for GlobalIdentifier we have getMainClass
    method.addInstruction(new GetFieldInstruction(type, outputClass.getName() + "/" + name));
  }

  @Override
  public void generateSetOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("ClosureIdentifierStatement Set"));
    outputClass.ensureFieldExists("private", name, type);
    method.addInstruction(new LocalLoadInstruction(type, 0)); // 'this', the current object
    // Note getClass, whereas for GlobalIdentifier we have getMainClass
    method.addInstruction(new PutFieldInstruction(type, outputClass.getName() + "/" + name));
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
