package rmk35.partIIProject.utility;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Arrays;

public class PerfectBinaryTreeTest extends TestCase
{ public PerfectBinaryTreeTest(String testName)
  { super(testName);
  }

  public static Test suite()
  { return new TestSuite(PerfectBinaryTreeTest.class);
  }

  public void testMerge()
  { PerfectBinaryTree<Integer>  leaf = new PerfectBinaryTreeLeaf<>(4);
    PerfectBinaryTree<String> node = new PerfectBinaryTreeNode<>(new PerfectBinaryTreeLeaf<>("Hello"));
    node.add(new PerfectBinaryTreeLeaf<>("World"));
    PerfectBinaryTree.map(leaf, node, (integer, string) ->
    { assertSame(integer, 4);
      assertTrue(Arrays.asList("Hello", "World").contains(string));
      return null;
    });
  }
}