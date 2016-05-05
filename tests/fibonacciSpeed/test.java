package fibonacciSpeed;

public class test extends javaTest.timedTest
{ public static void main(String... arguments)
  { new test().time();
  }

  @Override public long getEnd() { return 30; }
  @Override public long getSamples() { return 100; }
  @Override public void test(long number)
  { Fibonacci(number);
  }

  public static long Fibonacci(long n)
  { if (n == 0)
      return 1;
    else if (n == 1)
      return 1;
    else
      return Fibonacci(n-1) + Fibonacci(n-2);
  }
}