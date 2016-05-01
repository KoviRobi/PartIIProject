package rmk35.partIIProject.utility;

public interface FunctionalList<T>
{ public static <T> FunctionalList<T> list(T... elements)
  { FunctionalList<T> returnList = new FunctionalListNull<T>();
    for (int i = elements.length-1; i >= 0; i--)
    { returnList = new FunctionalListCons<T>(elements[i], returnList);
    }
    return returnList;
  }
  T head();
  FunctionalList<T> tail();
  boolean isEmpty();
  <U> U accept(FunctionalListVisitor<T, U> visitor);
}
