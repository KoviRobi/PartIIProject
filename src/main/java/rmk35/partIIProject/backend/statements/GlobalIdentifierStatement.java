package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
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
  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("GlobalIdentifierStatement Get"));
    // Note using mainClass, whereas for ClosureIdentifierStatement, we are using outputClass
    method.addInstruction(new GetStaticInstruction(type, mainClass.getName() + "/" + name));
  }

  @Override
  public void generateSetOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("GlobalIdentifierStatement Set"));
    // Note using mainClass, whereas for ClosureIdentifierStatement, we are using outputClass
    method.addInstruction(new PutStaticInstruction(type, mainClass.getName() + "/" + name));
  }

  @Override
  public String getName()
  { return name;
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }

  @Override
  public void ensureExistence(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { mainClass.ensureFieldExists("public static", name, type);
  }
}
