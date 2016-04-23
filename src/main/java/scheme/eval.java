package scheme;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.EnvironmentValue;
import rmk35.partIIProject.runtime.CallValue;
import rmk35.partIIProject.runtime.libraries.BinaryLambda;
import rmk35.partIIProject.runtime.libraries.VariadicLambda;
import rmk35.partIIProject.runtime.libraries.ReflectiveEnvironment;

import rmk35.partIIProject.middle.ASTMatcher;
import rmk35.partIIProject.middle.EnvironmentImporter;
import rmk35.partIIProject.middle.LibraryOrProgramme;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.InnerClass;
import rmk35.partIIProject.backend.statements.Statement;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

public class eval extends ReflectiveEnvironment
{ public eval() { bind(); }
  static int replCounter = 0;
  static LoadClass loader = new LoadClass();
  public static RuntimeValue eval =
  new BinaryLambda()
  { @Override
    public RuntimeValue run2(RuntimeValue expression, RuntimeValue environment)
    { try
      { MainClass mainClass = new MainClass("eval" + replCounter++);
        List<RuntimeValue> expressionList = new ArrayList<>(1);
        expressionList.add(expression);
        Statement programme = new LibraryOrProgramme((EnvironmentValue) environment, mainClass).compile(expressionList);
        programme.generateOutput(mainClass, mainClass.getMainInnerClass(), mainClass.getPrimaryMethod());
        for (InnerClass innerClass : mainClass.getInnerClasses())
        { if ( innerClass != mainClass.getMainInnerClass())
          { loader.defineClass(innerClass.getName(), innerClass.assembledByteCode());
          }
        }
        loader.defineClass(mainClass.getName(), mainClass.assembledByteCode());
        Class<?> mainInnerClass = loader.defineClass(mainClass.getMainInnerClass().getName(), mainClass.getMainInnerClass().assembledByteCode());
        Constructor<?> constructor = mainInnerClass.getConstructor(LambdaValue.class);
        constructor.setAccessible(true);
        LambdaValue mainInnerLambda = (LambdaValue) constructor.newInstance((LambdaValue) null);
        return new CallValue(mainInnerLambda, new NullValue());
      } catch (Exception e)
      { throw new RuntimeException(e);
      }
    }
  };

  public static RuntimeValue mutable_environment =
  new VariadicLambda()
  { public RuntimeValue run(RuntimeValue arguments)
    { EnvironmentValue returnEnvironment = new EnvironmentValue(/* mutable, for the moment */ true);
      EnvironmentImporter importer = new EnvironmentImporter(returnEnvironment);
      List<RuntimeValue> importSets = new ArrayList<>();
      ASTMatcher importDeclaration = new ASTMatcher("(import-set ...)", arguments);
      importDeclaration.get("import-set").forEach(value -> importSets.add(value));
      importer.importEnvironment(importSets);
      return returnEnvironment;
    }
  };

  public static RuntimeValue environment =
  new VariadicLambda()
  { public RuntimeValue run(RuntimeValue arguments)
    { EnvironmentValue returnEnvironment = (EnvironmentValue) ((LambdaValue) mutable_environment).apply(arguments);
      returnEnvironment.setMutable(false);
      return returnEnvironment;
    }
  };
}

class LoadClass extends ClassLoader
{ Class<?> defineClass(String name, byte[] bytes)
  { return defineClass(name, bytes, 0, bytes.length);
  }
}
