package rmk35.partIIProject.runtime;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class NameManglingTest extends TestCase
{ public NameManglingTest(String testName)
 { super(testName);
  }

  public static Test suite()
  { return new TestSuite(NameManglingTest.class);
  }

  public void testReversibility()
  { for (String s : new String[]{"hello", "world!", "does-this-work?"})
    { assertEquals(IdentifierValue.schemifyName(IdentifierValue.javaifyName(s)), s);
    }
  }

  public void testJavaifyName()
  { assertEquals(IdentifierValue.javaifyName("set!"), "scm_set$000021");
    assertEquals(IdentifierValue.javaifyName("car"), "scm_car");
    assertEquals(IdentifierValue.javaifyName("kebab-case"), "scm_kebab_case");
  }

  public void testSchemifyName()
  { assertEquals(IdentifierValue.schemifyName("scm_with_exception_handler"), "with-exception-handler");
  }
}
