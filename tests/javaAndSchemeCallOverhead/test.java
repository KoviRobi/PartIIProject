import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.numbers.NumberValue;

public class test
{ public static void main(String... arguments)
  { // Java calling java
/*    for (int i = 0; i < 100000; i++)
    { long start = System.nanoTime();
      javaCall.test.call(1, "foo");
      long end = System.nanoTime();
      System.out.println("Java to Java," + (end-start));
    }*/
    // Java calling Scheme
    for (int i = 0; i < 100000; i++)
    { long start = System.nanoTime();
      Compiler.schemeCall(new schemeCall.testf(null), 1, "foo");
      long end = System.nanoTime();
      System.out.println("Java to Scheme," + (end-start));
    }
  }
}