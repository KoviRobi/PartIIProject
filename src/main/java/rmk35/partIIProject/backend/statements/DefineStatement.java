package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.TrampolineValue;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import rmk35.partIIProject.backend.instructions.types.ObjectType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class DefineStatement extends Statement
{ IdentifierStatement variable;
  Statement value;

  public DefineStatement(IdentifierStatement variable, Statement value)
  { this.variable = variable;
    this.value = value;
  }

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("Define statement"));
    if (value == null)
    { new UnspecifiedValueStatement().generateOutput(mainClass, outputClass, method);
    } else
    { value.generateOutput(mainClass, outputClass, method);
    }
    method.addInstruction(new StaticCallInstruction(new ObjectType(RuntimeValue.class), TrampolineValue.class.getName().replace('.', '/') + "/bounceHelper", new ObjectType(RuntimeValue.class)));
    variable.ensureExistence(mainClass, outputClass, method);
    variable.generateSetOutput(mainClass, outputClass, method);
    // Push unspecified
    new UnspecifiedValueStatement().generateOutput(mainClass, outputClass, method);
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { return new TreeSet<String>();
  }
}
