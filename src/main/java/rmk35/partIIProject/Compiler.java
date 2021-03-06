package rmk35.partIIProject;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.EnvironmentValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.ValueHelper;

import rmk35.partIIProject.frontend.SchemeParser;

import rmk35.partIIProject.middle.AST;
import rmk35.partIIProject.middle.LibraryOrProgramme;
import rmk35.partIIProject.middle.EnvironmentImporter;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.TailCallSettings;
import rmk35.partIIProject.backend.statements.NonTailCalls;
import rmk35.partIIProject.backend.statements.Trampolining;
import rmk35.partIIProject.backend.statements.SchemeCallStack;

import java.util.List;
import java.io.IOException;

public class Compiler
{ public static void main(String[] arguments) throws Exception, IOException
  { if (arguments.length != 1)
    { System.out.println("Expecting only one argument, the file name to parse.");
      System.exit(1);
    }

    String fileName = arguments[0];
    String outputName = removeExtension(fileName);
    new Compiler(fileName, outputName);
  }

  static String removeExtension(String fileName)
  { int lastIndex = fileName.lastIndexOf('.');
    if (lastIndex == -1)
    { return fileName;
    } else
    { return fileName.substring(0, lastIndex);
    }
  }

  // Settings
  public static TailCallSettings tailCallSettings =
    new SettingsMatcher<TailCallSettings>("tailcall", "tailcalls")
      .addDefault(new SchemeCallStack())
      .tryMatch(new SchemeCallStack(), "fullcont", "full", "callcc", "on", "2")
      .tryMatch(new Trampolining(), "trampoline", "trampolining", "1")
      .tryMatch(new NonTailCalls(), "none", "no", "off", "0")
      .get();
  public static final EnvironmentValue simpleBaseEnvironment = new scheme.simple_base();
  public static final EnvironmentValue baseEnvironment =
    tailCallSettings instanceof NonTailCalls ? new scheme.base_notail()
    : tailCallSettings instanceof Trampolining ? new scheme.base_trampolining()
    : new scheme.base_stack();
  public static boolean intermediateCode = System.getenv("intermediate") != null || System.getenv("intermediateCode") != null;
  public static boolean profile = System.getenv("profile") != null;
  public static boolean timeStages = System.getenv("timeStages") != null;

  public Compiler(String fileName, String outputName) throws Exception, IOException
  { long start = 0;

    if (timeStages) start = System.nanoTime();
    MainClass mainClass = new MainClass(outputName);
    List<RuntimeValue> parsedFile = SchemeParser.parseFile(fileName);
    if (timeStages) System.out.print((System.nanoTime()-start) + ",");

    if (timeStages) start = System.nanoTime();
    EnvironmentValue environment = new EnvironmentValue(/* mutable */ true);
    Statement programme = new LibraryOrProgramme(environment, mainClass).compile(parsedFile);
    environment.warnUndefinedVariables();
    if (timeStages) System.out.print((System.nanoTime()-start) + ",");

    // Mutates mainClass
    if (timeStages) start = System.nanoTime();
    programme.generateOutput(mainClass, mainClass.getMainInnerClass(), mainClass.getPrimaryMethod());
    if (intermediateCode) mainClass.saveToDisk();
    mainClass.assembleToDisk();
    if (timeStages) System.out.println(System.nanoTime()-start);
  }

  public static Object schemeCall(LambdaValue function, Object... arguments)
  { RuntimeValue schemeArguments = new NullValue();
    for (int i = arguments.length-1; i >= 0; i--)
    { schemeArguments = new ConsValue(ValueHelper.toSchemeValue(arguments[i]), schemeArguments);
    }
    return tailCallSettings.apply(function, schemeArguments).toJavaValue();
  }
}

class SettingsMatcher<T>
{ T match = null;
  String optionValue = null;

  public SettingsMatcher(String... options)
  { for (int i = 0; i < options.length; i++)
    { optionValue = System.getenv(options[i]);
      if (optionValue != null) return;
    }
  }

  public SettingsMatcher<T> tryMatch(T matchValue, String... matchKeys)
  { for (int i = 0; i < matchKeys.length; i++)
    { if (matchKeys[i].equals(optionValue))
      { match = matchValue;
        break;
      }
    }
    return this;
  }

  public SettingsMatcher<T> addDefault(T defaultMatch)
  { match = defaultMatch;
    return this;
  }

  public T get()
  { return match;
  }
}
