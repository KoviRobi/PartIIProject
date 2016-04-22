package rmk35.partIIProject.runtime.libraries;

import rmk35.partIIProject.InternalCompilerException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.bindings.StaticFieldBinding;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;

import java.util.Map;
import java.util.Hashtable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class ReflectiveEnvironment extends EnvironmentValue
{ public void bind()
  { setMutable(true);
    for (Field field : this.getClass().getFields())
    { if (Modifier.isPublic(field.getModifiers()))
      { String currentClass = this.getClass().getName().replace('.', '/');
        String javaName = field.getName();
        String schemeName = IdentifierValue.schemifyName("scm_" + javaName);
        if (RuntimeValue.class.isAssignableFrom(field.getType()) && Modifier.isStatic(field.getModifiers()))
        { addBinding(schemeName, new StaticFieldBinding(currentClass, schemeName, javaName));
        } else if (Binding.class.isAssignableFrom(field.getType()))
        { try
          { addBinding(schemeName, (Binding) field.get(this));
          } catch (IllegalAccessException e)
          { throw new RuntimeException(e);
          }
        } else
        { throw new InternalCompilerException("Not sure how to deal with " + field + " as a binding");
        }
      }
    }
    setMutable(false);
  }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new NewObjectInstruction(this.getClass()));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new NonVirtualCallInstruction(voidType, this.getClass().getName().replace('.', '/') + "/<init>"));
  }
}
