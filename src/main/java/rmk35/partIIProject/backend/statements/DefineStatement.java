package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.UnspecifiedValue;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;

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
    value.generateOutput(mainClass, outputClass, method);
    Compiler.tailCallSettings.generateContinuation(method);
    variable.ensureExistence(mainClass, outputClass, method);
    variable.generateSetOutput(mainClass, outputClass, method);
    new RuntimeValueStatement(new UnspecifiedValue()).generateOutput(mainClass, outputClass, method);
  }
}
