package parameterizedVisitor;

public class StringVisitor implements visitor<String>
{ public String visit(visitee visitee)
  { return  "foo";
  }
}
