package rmk35.partIIProject.utility;

import java.util.List;
import java.util.ArrayList;

import lombok.Value;

@Value
public class FunctionalListNull<T> implements FunctionalList<T>
{ public <U> U accept(FunctionalListVisitor<T, U> visitor) { return visitor.visit(this); }
  public T head() { throw new UnsupportedOperationException("Getting the head of null!"); }
  public FunctionalList<T> tail() { throw new UnsupportedOperationException("Getting the tail of null!"); }
  public boolean isEmpty() { return true; }
  public String toString() { return "[]"; }
  public List<T> toJavaList() { return new ArrayList<>(); }
  public List<T> toJavaList(List<T> accumulator) { return accumulator; }
}
