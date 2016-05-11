package rmk35.partIIProject.utility;

import java.util.List;
import java.util.ArrayList;

import lombok.Value;

@Value
public class FunctionalListCons<T> implements FunctionalList<T>
{ T head;
  FunctionalList<T> tail;
  public<U>  U accept(FunctionalListVisitor<T, U> visitor) { return visitor.visit(this); }
  public T head() { return head; }
  public FunctionalList<T> tail() { return tail; }
  public boolean isEmpty() { return false; }
  public String toString()
  { FunctionalList<T> list = this.tail();
    StringBuilder returnValue = new StringBuilder("[");
    returnValue.append(this.head().toString());
    while (list instanceof FunctionalListCons)
    { returnValue.append(", ");
      returnValue.append(((FunctionalListCons) list).head().toString());
      list = ((FunctionalListCons) list).tail();
    }
    returnValue.append("]");
    return returnValue.toString();
  }
  public List<T> toJavaList() { return toJavaList(new ArrayList<>()); }
  public List<T> toJavaList(List<T> accumulator) { accumulator.add(head); return tail.toJavaList(accumulator); }
}
