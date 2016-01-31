package sumTypes;

public interface Visitor<A, B, T>
{ public T visit(OptionA<A, B> a);
  public T visit(OptionB<A, B> b);
}