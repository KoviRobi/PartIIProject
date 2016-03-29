package rmk35.partIIProject.utility;

import java.util.function.Consumer;
import java.util.function.BiFunction;

/* A binary tree that always maintains
 "all paths from root to leaves have the same length"
 */
/* Immutable */
public abstract class PerfectBinaryTree<T> implements Cloneable
{ public abstract int getDepth();
  public abstract void add(PerfectBinaryTree<T> other);
  public abstract <U> PerfectBinaryTree<U> foldLeavesLeft(BiFunction<U, T, U> function, U start);
  public abstract <U> PerfectBinaryTree<U> foldLeavesRight(BiFunction<U, T, U> function, U start);
  public abstract PerfectBinaryTree<T> walk(int index);
  public abstract void forEach(Consumer<T> function);
  public abstract PerfectBinaryTree<T> clone();
  abstract int getWidth();
  abstract boolean canMap(PerfectBinaryTree<?> other);

  public static <U, V, W> PerfectBinaryTree<W> map(PerfectBinaryTree<U> tree1, PerfectBinaryTree<V> tree2, BiFunction<U, V, W> function)
  { if (tree1 instanceof PerfectBinaryTreeLeaf && tree2 instanceof PerfectBinaryTreeLeaf)
    { return new PerfectBinaryTreeLeaf<W>(function.apply(((PerfectBinaryTreeLeaf<U>) tree1).getValue(), ((PerfectBinaryTreeLeaf<V>) tree2).getValue()));
    } else
    { if (! tree1.canMap(tree2)) throw new UnsupportedOperationException("Mapping over trees that are not the same shape");
      int width = Math.max(tree1.getWidth(), tree2.getWidth());
      PerfectBinaryTree<W> returnValue = null;
      for (int i = 0; i < width; i++)
      { if (returnValue == null)
        { returnValue = new PerfectBinaryTreeNode<W>(map(tree1.walk(i), tree2.walk(i), function));
        } else
        { returnValue.add(map(tree1.walk(i), tree2.walk(i), function));
        }
      }
      return returnValue;
    }
  }
}
