package scheme;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.EnvironmentValue;
import rmk35.partIIProject.runtime.ValueHelper;
import rmk35.partIIProject.runtime.libraries.BinaryLambda;
import rmk35.partIIProject.runtime.libraries.UnaryLambda;
import rmk35.partIIProject.runtime.libraries.ReflectiveEnvironment;

import rmk35.partIIProject.middle.ASTMatcher;
import rmk35.partIIProject.middle.EnvironmentImporter;
import rmk35.partIIProject.middle.LibraryOrProgramme;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.InnerClass;
import rmk35.partIIProject.backend.statements.Statement;

import java.util.List;
import java.util.ArrayList;

public class eval extends ReflectiveEnvironment
{ public eval() { bind(); }
  static int replCounter = 0;
  public RuntimeValue eval =
  new BinaryLambda()
  { @Override
    public RuntimeValue run(RuntimeValue expression, RuntimeValue environment)
    { try
      { MainClass mainClass = new MainClass("eval" + replCounter++);
        List<RuntimeValue> expressionList = new ArrayList<>(1);
        expressionList.add(expression);
        Statement programme = new LibraryOrProgramme((EnvironmentValue) environment).compile(expressionList);
        programme.generateOutput(mainClass, mainClass.getMainInnerClass(), mainClass.getPrimaryMethod());
        LoadClass loader = new LoadClass();
        for (InnerClass innerClass : mainClass.getInnerClasses())
        { if ( innerClass != mainClass.getMainInnerClass())
          { loader.defineClass(innerClass.getName(), innerClass.assembledByteCode());
          }
        }
        Class<?> mainInnerClass = loader.defineClass(mainClass.getMainInnerClass().getName(), mainClass.getMainInnerClass().assembledByteCode());
        return ValueHelper.toSchemeValue(mainInnerClass);
      } catch (Exception e)
      { throw new RuntimeException(e);
      }
    }
  };

  class LoadClass extends ClassLoader
  { Class<?> defineClass(String name, byte[] bytes)
    { return defineClass(name, bytes, 0, bytes.length);
    }
  }

  public RuntimeValue mutable_environment =
  new LambdaValue()
  { public RuntimeValue apply(RuntimeValue arguments)
    { EnvironmentValue returnEnvironment = new EnvironmentValue(/* mutable, for the moment */ true);
      EnvironmentImporter importer = new EnvironmentImporter(returnEnvironment);
      List<RuntimeValue> importSets = new ArrayList<>();
      ASTMatcher importDeclaration = new ASTMatcher("(import-set ...)", arguments);
      importDeclaration.get("import-set").forEach(value -> importSets.add(value));
      importer.importEnvironment(importSets);
      return returnEnvironment;
    }
  };

  public RuntimeValue environment =
  new LambdaValue()
  { public RuntimeValue apply(RuntimeValue arguments)
    { EnvironmentValue returnEnvironment = (EnvironmentValue) ((LambdaValue) mutable_environment).apply(arguments);
      returnEnvironment.setMutable(false);
      return returnEnvironment;
    }
  };
}
