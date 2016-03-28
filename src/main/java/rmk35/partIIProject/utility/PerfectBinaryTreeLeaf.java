package rmk35.partIIProject.utility;

import fj.data.List;

import java.util.function.BiFunction;

/* A binary tree that always maintains
 "all paths from root to leaves have the same length"
 */
/* Immutable */
public class PerfectBinaryTreeLeaf<T> extends PerfectBinaryTree<T>
{ final T value;

  public PerfectBinaryTreeLeaf(T value)
  { this.value = value;
  }

  public int getDepth() { return 0; }
  public int getWidth() { return 0; }
  public T getValue() { return value; }

  public PerfectBinaryTree<T> add(PerfectBinaryTree<T> other)
  { throw new UnsupportedOperationException("Can't add to a leaf");
  }

  public <U> PerfectBinaryTree<U> foldRight(BiFunction<U, T, U> function, U start)
  { throw new UnsupportedOperationException("Can't fold a leaf");
  }

  PerfectBinaryTree<T> walk(int index)
  { return this;
  }

  boolean canMap(PerfectBinaryTree<?> other)
  { return true;
  }

  @Override
  public String toString()
  { return "Leaf(" + value.toString() + ")";
  }
}
