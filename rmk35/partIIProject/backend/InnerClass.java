package rmk35.partIIProject.backend;

import java.util.List;

public class InnerClass implements OutputClass
{ String name;
  StringBuilder fields;
  StringBuilder mainMethod;
  List<Identifier> closureVariables;
  int uniqueNumber = 0;

  public InnerClass(String name, List<Identifier> closureVariables)
  { this(name, closureVariables, new StringBuilder(), new StringBuilder());
  }
  public InnerClass(String name, List<Identifier> closureVariables, StringBuilder fields, StringBuilder mainMethods)
  { this.name = name;
    this.closureVariables = closureVariables;
    this.fields = fields;
    this.mainMethod = mainMethod;
  }

  public String uniqueID()
  { return "Inner" + name + Integer.toString(uniqueNumber);
  }

  public void addToPrimaryMethod(String value)
  { mainMethod.append(value);
  }
}
