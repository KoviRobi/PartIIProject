package sumTypes;

public class Main
{ public static void main(String[] arguments)
  { Sum<String, Integer> a = Sum.makeA("Hello", (Integer) null);
    Sum<String, Integer> b = Sum.makeA((String) null, 234);
    a.accept(new TestVisitor());
    b.accept(new TestVisitor());
  }
}