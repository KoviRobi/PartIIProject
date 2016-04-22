package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.LambdaValue;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.SwapInstruction;
import rmk35.partIIProject.backend.instructions.LocalLoadInstruction;
import rmk35.partIIProject.backend.instructions.GetFieldInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.lambdaValueType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class LocalIdentifierStatement extends FieldIdentifierStatement
{ int inParentNo;

  public LocalIdentifierStatement(String containingClass, String schemeName, String javaName, int inParentNo)
  { super(containingClass, schemeName, javaName);
    this.inParentNo = inParentNo;
  }

  void getContainer(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("Variable " + schemeName));
    // Load 'this'
    method.addInstruction(new LocalLoadInstruction(lambdaValueType, 0));
    // Load parents, if necessary (e.g. a closure)
    for (int i = 0; i < inParentNo; i++)
    { method.addInstruction(new GetFieldInstruction(lambdaValueType, LambdaValue.class.getName().replace('.', '/') + "/parent"));
    }
    if (inParentNo > 0)
    { method.addInstruction(new CheckCastInstruction(containingClass));
    }
  }

  @Override
  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("LocalIdentifier Get"));
    getContainer(mainClass, outputClass, method);
    super.generateOutput(mainClass, outputClass, method);
  }

  /* Assumes variable to set to is on top of the stack */
  @Override
  public void generateSetOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("LocalIdentifier Set"));
    getContainer(mainClass, outputClass, method);
    // Swap value and object ref
    method.addInstruction(new SwapInstruction());
    super.generateSetOutput(mainClass, outputClass, method);
  }

  @Override
  public String getName()
  { return schemeName;
  }

  @Override
  public void ensureExistence(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { if (inParentNo == 0)
    { outputClass.ensureFieldExists("public", getJavaName(), runtimeValueType);
    }
  }
}
