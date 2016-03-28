package rmk35.partIIProject.utility;

import fj.data.List;
import fj.data.Java8;

import java.util.function.BiFunction;

/* A binary tree that always maintains
 "all paths from root to leaves have the same length"
 */
/* Immutable */
public class PerfectBinaryTreeNode<T> extends PerfectBinaryTree<T>
{ final int depth;
  final List<PerfectBinaryTree<T>> children;

  public PerfectBinaryTreeNode(PerfectBinaryTree<T> child)
  { depth = child.getDepth()+1;
    children = List.list(child);
  }
  PerfectBinaryTreeNode(int depth, List<PerfectBinaryTree<T>> children)
  { this.depth = depth;
    this.children = children;
  }
  List<PerfectBinaryTree<T>> getChildren() { return children; }

  public int getDepth() { return depth; }
  public int getWidth() { return children.length(); }

  public PerfectBinaryTree<T> add(PerfectBinaryTree<T> other)
  { if (other.getDepth() == depth-1)
    { return new PerfectBinaryTreeNode<T>(depth, children.cons(other));
    } else
    { throw new UnsupportedOperationException("This would unbalance the tree depths");
    }
  }

  PerfectBinaryTree<T> walk(int index)
  { return children.index(index);
  }

  boolean canMap(PerfectBinaryTree<?> other)
  { return other instanceof PerfectBinaryTreeLeaf
      || ((PerfectBinaryTreeNode<?>) other).getWidth() == getWidth();
  }

  public <U> PerfectBinaryTree<U> foldRight(BiFunction<U, T, U> function, U start)
  { if (depth == 1)
    { return new PerfectBinaryTreeLeaf<U>
        (children
          .map(child -> ((PerfectBinaryTreeLeaf<T>) child).value)
          .foldLeft(Java8.BiFunction_F2(function), start));
    } else
    { return new PerfectBinaryTreeNode<U>(depth-1, children.map(child -> child.foldRight(function, start)));
    }
  }

  @Override
  public String toString()
  { return "Node" + depth + children.toString();
  }
}
