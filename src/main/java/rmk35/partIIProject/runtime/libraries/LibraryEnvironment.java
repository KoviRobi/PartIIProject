package rmk35.partIIProject.runtime.libraries;

import rmk35.partIIProject.Compiler;
import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.ASTMatcher;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectIdentifierVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListMapVisitor;
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

public abstract class LibraryEnvironment extends EnvironmentValue
{ public void bind()
  { setMutable(true);
    String currentClass = this.getClass().getName().replace('.', '/');
    for (RuntimeValue export : getExports().accept(new ASTListMapVisitor<>(x -> x)))
    { if (export instanceof IdentifierValue)
      { String schemeName = ((IdentifierValue) export).getValue();
        String javaName = IdentifierValue.javaifyName(schemeName);
        addBinding(schemeName, new StaticFieldBinding(currentClass, schemeName, javaName));
        continue;
      }
      ASTMatcher renameExport = new ASTMatcher(this, this, "(rename from to)", export, "rename");
      if (renameExport.matched())
      { String fromName = renameExport.transform("from").accept(new ASTExpectIdentifierVisitor()).getValue();
        String toName = renameExport.transform("to").accept(new ASTExpectIdentifierVisitor()).getValue();
        addBinding(toName, new StaticFieldBinding(currentClass, fromName, IdentifierValue.javaifyName(fromName)));
        continue;
      }
      throw new SyntaxErrorException("Bad export: " + export, export.getSourceInfo());
    }
    setMutable(false);
  }

  public abstract RuntimeValue getExports();

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new NewObjectInstruction(this.getClass()));
    method.addInstruction(new DupInstruction());
    method.addInstruction(new NonVirtualCallInstruction(voidType, this.getClass(), "<init>"));
  }
}
