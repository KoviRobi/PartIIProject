package rmk35.partIIProject.utility;

import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.function.Consumer;
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
    children = new ArrayList<>();
    children.add(child);
  }
  PerfectBinaryTreeNode(int depth, List<PerfectBinaryTree<T>> children)
  { this.depth = depth;
    this.children = children;
  }
  List<PerfectBinaryTree<T>> getChildren() { return children; }

  public int getDepth() { return depth; }

  public void add(PerfectBinaryTree<T> other)
  { if (other.getDepth() == depth-1)
    { children.add(other);
    } else
    { throw new UnsupportedOperationException("This would unbalance the tree depths");
    }
  }

  public PerfectBinaryTreeNode<T> clone()
  { List<PerfectBinaryTree<T>> clonedChildren = new ArrayList<>(children.size());
    for (PerfectBinaryTree<T> child : children)
    { clonedChildren.add(child.clone());
    }
    return new PerfectBinaryTreeNode<>(depth, clonedChildren);
  }

  public PerfectBinaryTree<T> walk(int index)
  { return children.get(index);
  }

  public void forEach(Consumer<T> function)
  { for (PerfectBinaryTree<T> child : children)
    { child.forEach(function);
    }
  }

  int getWidth() { return children.size(); }
  boolean canMap(PerfectBinaryTree<?> other)
  { return other instanceof PerfectBinaryTreeLeaf
      || ((PerfectBinaryTreeNode<?>) other).getWidth() == getWidth();
  }

  public <U> PerfectBinaryTree<U> foldLeavesLeft(BiFunction<U, T, U> function, U start)
  { if (depth == 1)
    { for (PerfectBinaryTree<T> child : children)
      { T value = ((PerfectBinaryTreeLeaf<T>) child).getValue();
        start = function.apply(start, value);
      }
      return new PerfectBinaryTreeLeaf<U>(start);
    } else
    { List<PerfectBinaryTree<U>> newChildren = new ArrayList<>(children.size());
      for (PerfectBinaryTree<T> child : children)
      { newChildren.add(child.foldLeavesLeft(function, start));
      }
      return new PerfectBinaryTreeNode<U>(getDepth()-1, newChildren);
    }
  }

  public <U> PerfectBinaryTree<U> foldLeavesRight(BiFunction<U, T, U> function, U start)
  { if (depth == 1)
    { ListIterator<PerfectBinaryTree<T>> iterator = children.listIterator(children.size());
      while (iterator.hasPrevious())
      { T value = ((PerfectBinaryTreeLeaf<T>) iterator.previous()).getValue();
        start = function.apply(start, value);
      }
      return new PerfectBinaryTreeLeaf<U>(start);
    } else
    { // Only fold leaves rightwards
      List<PerfectBinaryTree<U>> newChildren = new ArrayList<>(children.size());
      for (PerfectBinaryTree<T> child : children)
      { newChildren.add(child.foldLeavesRight(function, start));
      }
      return new PerfectBinaryTreeNode<U>(getDepth()-1, newChildren);
    }
  }

  @Override
  public String toString()
  { return "Node" + depth + children.toString();
  }
}
