package macroMatchSpeed;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.frontend.SchemeParser;

import rmk35.partIIProject.middle.astMacroMatchVisitor.ASTCompilePatternVisitor;
import rmk35.partIIProject.middle.astMacroMatchVisitor.ASTMatchVisitor;
import rmk35.partIIProject.middle.bindings.EllipsisBinding;

import java.util.Arrays;

public class test extends javaTest.timedTest
{ public long step = 200;
  public RuntimeValue template;
  public ASTMatchVisitor pattern;

  public static void main(String... arguments)
  { new test().time();
  }
  test()
  { EnvironmentValue environment = new EnvironmentValue(Compiler.simpleBaseEnvironment, /* mutable */ true);
    environment.addBinding("...", new EllipsisBinding());
    pattern = SchemeParser.read("(a ...)")
      .accept(new ASTCompilePatternVisitor(Arrays.asList(), environment)).getFirst();
    pattern.setUseEnvironment(environment);
    template = new NullValue();
  }
  @Override public long getStart() { return 0; }
  @Override public long getEnd() { return 100*200; }
  @Override public long getIncrement() { return 200; }
  @Override public void test(long number)
  { if (template.accept(pattern) == null) throw new RuntimeException("No match!");
  }
  @Override public void postSampling()
  { for (int incremented = 0; incremented < step; incremented++)
    { template = new ConsValue(new IdentifierValue("a"), template);
    }
  }
}