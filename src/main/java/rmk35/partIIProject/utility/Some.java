package rmk35.partIIProject.utility;

import java.util.function.Function;

import lombok.Value;

@Value
public class Some<T> implements Option<T>
{ T value;
  public boolean isSome() { return true; }
  public <R> Option<R> map(Function<T, R> function) { return new Some<R>(function.apply(value)); }
  public T getValue() { return value; }
}