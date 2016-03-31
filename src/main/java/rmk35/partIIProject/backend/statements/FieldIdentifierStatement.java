package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
import rmk35.partIIProject.backend.instructions.GetFieldInstruction;
import rmk35.partIIProject.backend.instructions.SwapInstruction;
import rmk35.partIIProject.backend.instructions.PutFieldInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

/**
 * Identifiers from elsewhere
 */
@ToString
public class FieldIdentifierStatement extends IdentifierStatement
{ String containingClass;
  String name;

  public FieldIdentifierStatement(String containingClass, String name)
  { this.containingClass = containingClass;
    this.name = name;
  }

  @Override
  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("FieldIdentifierStatement Get"));
    method.addInstruction(new GetFieldInstruction(runtimeValueType, getContainingClass() + "/" + getName()));
  }

  @Override
  public void generateSetOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { throw new UnsupportedOperationException("The intention of this class is to be used by EnvironmentImporter to copy bindings to be global bindings, so set is unsupported");
  }

  @Override
  public String getName()
  { return name;
  }

  public String getContainingClass()
  { return containingClass;
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<>();
  }

  @Override
  public void ensureExistence(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { return; // This binding is used for classes beyond our control
  }
}
