package sumTypes;

public class OptionA<A, B> extends Sum<A, B>
{ public <T> T accept(Visitor<A, B, T> visitor)
  { return visitor.visit(this);
  }
}