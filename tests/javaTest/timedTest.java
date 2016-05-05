package javaTest;

public abstract class timedTest
{ public long getStart() { return 0; };
  public abstract long getEnd();
  public long getSamples() { return 500; }
  public long getIncrement() { return 1; }
  public abstract void test(long iteration);
  public void preSampling() {};
  public void postSampling() {};
  public void time()
  { long end = getEnd();
    long samples = getSamples();
    for (long iteration = getStart(); iteration <= end; iteration += getIncrement())
    { double running_mean = 0;
      double running_variance = 0;
      System.err.println("Doing " + iteration + "/" + end);
      preSampling();
      // X_n and S_n defined as sum from i=1 to i=n (inclusive)
      for (int sample_no = 1; sample_no <= samples; sample_no++)
      { long startTime = System.nanoTime();
        test(iteration);
        long endTime = System.nanoTime();
        // From Computer Systems Modelling example sheet, example 1.4
        double last_running_mean = running_mean;
        running_mean = running_mean + (((double) (endTime-startTime)) - running_mean)/((double) sample_no);
        running_variance = running_variance + (((double) (endTime-startTime)) - last_running_mean)*(((double) (endTime-startTime)) - running_mean);
      }
      postSampling();
      System.out.println(iteration + ", " + running_mean + ", " + (Math.sqrt(running_variance/((double) (samples-1)))));
    }
  }
}