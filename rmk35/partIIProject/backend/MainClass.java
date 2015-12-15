package rmk35.partIIProject.backend;

import java.util.List;
import java.util.ArrayList;

public class MainClass implements OutputClass
{ StringBuilder fields;
  StringBuilder mainMethod;
  List<InnerClass> innerClasses;
  int uniqueNumber = 0;

  public MainClass()
  { this(new StringBuilder(), new StringBuilder(), new ArrayList<InnerClass>());
  }
  public MainClass(StringBuilder fields, StringBuilder mainMethods, List<InnerClass> innerClasses)
  { this.fields = fields;
    this.mainMethod = mainMethod;
    this.innerClasses = innerClasses;
  }

  public void addToPrimaryMethod(String value)
  { mainMethod.append(value);
  }

  public String uniqueID()
  { uniqueNumber++;
    return "Main" + Integer.toString(uniqueNumber);
  }

  public String toString()
  {
  }
}
