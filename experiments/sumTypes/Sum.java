package sumTypes;

public abstract class Sum
{ public static <A, B> Sum<A, B> makeA(A a, B b)
  { return new OptionA<A, B>(a);
  }

  public static <A, B> Sum<A, B> makeB(A a, B b)
  { return new OptionB<A, B>(b);
  }

  static public <T> T accept(Visitor<A, B, T> visitor);
}