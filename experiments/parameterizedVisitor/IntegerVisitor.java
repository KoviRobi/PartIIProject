package parameterizedVisitor;

public class IntegerVisitor implements visitor<Integer>
{ public Integer visit(visitee visitee)
  { return 42;
  }
}
