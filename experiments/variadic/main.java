public class main
{ public static void main(String[] arguments)
  { test("foo", "bar");
  }

  public static void test(String... foo)
  { System.out.println(foo);
  }
}