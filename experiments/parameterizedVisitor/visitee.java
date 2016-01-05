package parameterizedVisitor;

public class visitee
{ public <T> T accept(visitor<T> visitor)
  { return visitor.visit(this);
  }
}