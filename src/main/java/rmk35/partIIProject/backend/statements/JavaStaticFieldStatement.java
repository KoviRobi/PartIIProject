package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.runtime.StringValue;
import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.TrampolineValue;
import rmk35.partIIProject.runtime.ValueHelper;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.CheckCastInstruction;
import rmk35.partIIProject.backend.instructions.VirtualCallInstruction;
import rmk35.partIIProject.backend.instructions.StaticCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.stringType;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.runtimeValueType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class JavaStaticFieldStatement extends Statement
{ Statement className;
  Statement fieldName;

  public JavaStaticFieldStatement(Statement className, Statement fieldName)
  { this.className = className;
    this.fieldName = fieldName;
  }

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("JavaFieldStatement"));
    className.generateOutput(mainClass, outputClass, method);
    method.addInstruction(new StaticCallInstruction(runtimeValueType, TrampolineValue.class.getName().replace('.', '/') + "/bounceHelper", runtimeValueType));
    method.addInstruction(new CheckCastInstruction(StringValue.class));
    method.addInstruction(new VirtualCallInstruction(stringType, StringValue.class.getName().replace('.', '/') + "/getValue"));
    fieldName.generateOutput(mainClass, outputClass, method);
    method.addInstruction(new StaticCallInstruction(runtimeValueType, TrampolineValue.class.getName().replace('.', '/') + "/bounceHelper", runtimeValueType));
    method.addInstruction(new CheckCastInstruction(StringValue.class));
    method.addInstruction(new VirtualCallInstruction(stringType, StringValue.class.getName().replace('.', '/') + "/getValue"));
    method.addInstruction(new StaticCallInstruction(runtimeValueType, JavaStaticFieldStatement.class.getName().replace('.', '/') + "/getStaticField", stringType, stringType));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> returnValue = new TreeSet<>();
    returnValue.addAll(className.getFreeIdentifiers());
    returnValue.addAll(fieldName.getFreeIdentifiers());
    return returnValue;
  }

  public static RuntimeValue getStaticField(String className, String fieldName)
  { try
    { return ValueHelper.toSchemeValue(Class.forName(className).getField(fieldName).get(Class.forName(className)));
    } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException exception)
    { throw new RuntimeException("Can't get static field", exception);
    }
  }
}
