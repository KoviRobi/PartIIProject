package scheme;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.RuntimeValue;
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
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

public class cxr extends ReflectiveEnvironment
{ public cxr() { bind(); }
  public static RuntimeValue caaar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(cell))); }
  };
  public static RuntimeValue caadr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(cell))); }
  };
  public static RuntimeValue cadar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(cell))); }
  };
  public static RuntimeValue cdaar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(cell))); }
  };
  public static RuntimeValue caddr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(cell))); }
  };
  public static RuntimeValue cddar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(cell))); }
  };
  public static RuntimeValue cdadr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(cell))); }
  };
  public static RuntimeValue cdddr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(cell))); }
  };

  public static RuntimeValue caaaar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(cell)))); }
  };
  public static RuntimeValue caadar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(cell)))); }
  };
  public static RuntimeValue cadaar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(cell)))); }
  };
  public static RuntimeValue cdaaar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(cell)))); }
  };
  public static RuntimeValue caddar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(cell)))); }
  };
  public static RuntimeValue cddaar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(cell)))); }
  };
  public static RuntimeValue cdadar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(cell)))); }
  };
  public static RuntimeValue cdddar =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(cell)))); }
  };

  public static RuntimeValue caaadr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(cell)))); }
  };
  public static RuntimeValue caaddr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(cell)))); }
  };
  public static RuntimeValue cadadr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(cell)))); }
  };
  public static RuntimeValue cdaadr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(cell)))); }
  };
  public static RuntimeValue cadddr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(cell)))); }
  };
  public static RuntimeValue cddadr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(cell)))); }
  };
  public static RuntimeValue cdaddr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.car).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(cell)))); }
  };
  public static RuntimeValue cddddr =
  new UnaryLambda()
  { @Override
    public RuntimeValue run1(RuntimeValue cell) { return ((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(((UnaryLambda) lists.cdr).run1(cell)))); }
  };
}
