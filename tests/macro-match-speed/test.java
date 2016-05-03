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

public class test
{ public static void main(String... arguments)
  { EnvironmentValue environment = new EnvironmentValue(Compiler.simpleBaseEnvironment, /* mutable */ true);
    environment.addBinding("...", new EllipsisBinding());
    ASTMatchVisitor pattern = SchemeParser.read("(a ...)")
      .accept(new ASTCompilePatternVisitor(Arrays.asList(), environment)).getFirst();

    pattern.setUseEnvironment(environment);
    RuntimeValue template = new NullValue();

    double running_mean = 0;
    double running_variance = 0;

    int step = 200;
    int samples = 500;

    for (long len_over_step = 0; len_over_step < (long)100; len_over_step++)
    { running_mean = 0;
      running_variance = 0;
      System.err.println("Doing " + len_over_step);
      // X_n and S_n defined as sum from i=1 to i=n (inclusive)
      for (int sample_no = 1; sample_no <= samples; sample_no++)
      { long startTime = System.nanoTime();
        if (template.accept(pattern) == null) throw new RuntimeException("No match!");
        long endTime = System.nanoTime();
        // From Computer Systems Modelling example sheet, example 1.4
        double last_running_mean = running_mean;
        running_mean = running_mean + (((double) (endTime-startTime)) - running_mean)/((double) sample_no);
        running_variance = running_variance + (((double) (endTime-startTime)) - last_running_mean)*(((double) (endTime-startTime)) - running_mean);
      }

      System.out.println((len_over_step*step) + ", " + running_mean + ", " + (Math.sqrt(running_variance/((double) (samples-1)))));

      for (int incremented = 0; incremented < step; incremented++)
      { template = new ConsValue(new IdentifierValue("a"), template);
      }
    }
  }
}