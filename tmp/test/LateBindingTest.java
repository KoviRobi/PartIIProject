package test;

import java.util.List;

public class LateBindingTest
{ String message;

  public LateBindingTest()
  { message = "Nullary constructor";
  }

  public LateBindingTest(Object object)
  { message = "Called with object: " + object.toString();
  }

  public LateBindingTest(String string)
  { message = "Called with string: " + string;
  }

  public void printMessage()
  { System.out.println(message);
  }

  public void printMessage(Object object)
  { System.out.println(message + ", but what does " + object + " mean?");
  }

  public void printMessage(Integer integer)
  { System.out.println(message + " at ID " + integer);
  }

  public static void printStaticMessage(List<Object> list)
  { System.out.println("You gave me " + list);
  }
}