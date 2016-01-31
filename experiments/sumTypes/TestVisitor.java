package sumTypes;

public class TestVisitor implements Visitor<String, Integer, Void>
{ public Void visit(OptionA<String, Integer> a)
  { System.out.println("Got A, string:" + a);
    return null;
  }

  public Void visit(OptionB<String, Integer> b)
  { System.out.println("Got B, int:" + b);
    return null;
  }
}