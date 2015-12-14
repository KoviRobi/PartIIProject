package rmk35.partIIProject.backend;

import java.util.List;
import java.util.ArrayList;

public class OutputClass
{ StringBuilder fields;
  StringBuilder mainMethod;
  List<InnerClass> innerClasses;
  int uniqueNumber = 0;

  public OutputClass()
  { this(new StringBuilder(), new StringBuilder(), new ArrayList<InnerClass>());
  }
  public OutputClass(StringBuilder fields, StringBuilder mainMethods, List<InnerClass> innerClasses)
  { this.fields = fields;
    this.mainMethod = mainMethod;
    this.innerClasses = innerClasses;
  }

  public void addToMainMethod(String value)
  { mainMethod.append(value);
  }

  public int uniqueNumber()
  { return uniqueNumber++;
  }
}
