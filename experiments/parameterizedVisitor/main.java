package parameterizedVisitor;

public class main
{ public static void main(String[] arguments)
  { visitee visitee = new visitee();
    System.out.println(visitee.accept(new IntegerVisitor()));
    System.out.println(visitee.accept(new StringVisitor()));
  }
}