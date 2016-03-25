package test;

import java.util.List;

public class JavaCallTest
{ String message;
  public static final String secretMessage = "Shh, don't tell anyone!";

  public JavaCallTest()
  { message = "Nullary constructor";
  }

  public JavaCallTest(Object object)
  { message = "Constructed with object: " + object.toString();
  }

  public JavaCallTest(String string)
  { message = "Constructed with string: " + string;
  }

  public JavaCallTest(List<Object> list)
  { message = "Constructed with string: " + list.toString();
  }

  public void printMessage()
  { System.out.println(message);
  }

  public void printMessage(Object object)
  { System.out.println(message + ", but what does " + object + " mean?");
  }

  public void printMessage(Integer integer)
  { System.out.println(message + " at ID " + integer + ".");
  }

  public static void printStaticMessage()
  { System.out.println("You gave me nothing!");
  }

  public static void printStaticMessage(List<Object> list)
  { System.out.println("You gave me " + list + ".");
  }
}