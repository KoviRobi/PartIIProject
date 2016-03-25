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
import rmk35.partIIProject.backend.instructions.types.ObjectType;

import java.util.Collection;
import java.util.TreeSet;

import lombok.ToString;

@ToString
public class JavaFieldStatement extends Statement
{ Statement object;
  Statement fieldName;

  private static final ObjectType stringType = new ObjectType(String.class);
  private static final ObjectType runtimeValueType = new ObjectType(RuntimeValue.class);

  public JavaFieldStatement(Statement object, Statement fieldName)
  { this.object = object;
    this.fieldName = fieldName;
  }

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("JavaFieldStatement"));
    object.generateOutput(mainClass, outputClass, method);
    method.addInstruction(new StaticCallInstruction(new ObjectType(RuntimeValue.class), TrampolineValue.class.getName().replace('.', '/') + "/bounceHelper", new ObjectType(RuntimeValue.class)));
    fieldName.generateOutput(mainClass, outputClass, method);
    method.addInstruction(new CheckCastInstruction(StringValue.class));
    method.addInstruction(new VirtualCallInstruction(stringType, StringValue.class.getName().replace('.', '/') + "/getValue"));
    method.addInstruction(new StaticCallInstruction(runtimeValueType, JavaFieldStatement.class.getName().replace('.', '/') + "/getField", runtimeValueType, stringType));
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> returnValue = new TreeSet<>();
    returnValue.addAll(object.getFreeIdentifiers());
    returnValue.addAll(fieldName.getFreeIdentifiers());
    return returnValue;
  }

  public static RuntimeValue getField(RuntimeValue object, String fieldName)
  { try
    { return ValueHelper.toSchemeValue(object.getClass().getField(fieldName).get(object));
    } catch (NoSuchFieldException | IllegalAccessException exception)
    { throw new RuntimeException(exception);
    }
  }
}
