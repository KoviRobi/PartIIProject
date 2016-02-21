package rmk35.partIIProject.utility;

import java.util.function.Function;

public interface Option<T>
{ boolean isSome();
  <R> Option<R> map(Function<T, R> function);
  T getValue();
}