package rmk35.partIIProject.utility;

import java.util.function.Function;

import lombok.Value;

@Value
public class None<T> implements Option<T>
{ public boolean isSome() { return false; }
  public <R> Option<R> map(Function<T, R> function) { return new None<R>(); }
  public T getValue() { throw new RuntimeException("No value for None option type"); }
}