package rmk35.partIIProject.utility;

public interface FunctionalListVisitor<T, U>
{ public U visit(FunctionalListCons<T> cons);
  public U visit(FunctionalListNull<T> nullValue);
}
