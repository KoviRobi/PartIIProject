package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.GetStaticInstruction;
import rmk35.partIIProject.backend.instructions.PutStaticInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class GlobalIdentifierStatement extends StaticFieldIdentifierStatement
{ public GlobalIdentifierStatement(String containingClass, String schemeName, String javaName) { super(containingClass, schemeName, javaName); }

  @Override
  public void ensureExistence(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { mainClass.ensureFieldExists("public static", getJavaName(), runtimeValueType);
  }
}
