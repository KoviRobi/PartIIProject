package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.GetFieldInstruction;
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
  String schemeName;
  String javaName;

  public FieldIdentifierStatement(String containingClass, String schemeName, String javaName)
  { this.containingClass = containingClass;
    this.schemeName = schemeName;
    this.javaName = javaName;
  }

  @Override
  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("FieldIdentifierStatement Get"));
    method.addInstruction(new GetFieldInstruction(runtimeValueType, getContainingClass() + "/" + getJavaName()));
  }

  @Override
  public void generateSetOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("FieldIdentifierStatement Set"));
    method.addInstruction(new PutFieldInstruction(runtimeValueType, getContainingClass() + "/" + getJavaName()));
  }

  @Override
  public String getName()
  { return schemeName;
  }

  @Override
  public String getJavaName()
  { return javaName;
  }

  public String getContainingClass()
  { return containingClass;
  }

  @Override
  public void ensureExistence(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { throw new UnsupportedOperationException("Field binding already exists, this method is called by 'define' with should not have happened for a field binding!");
  }
}
