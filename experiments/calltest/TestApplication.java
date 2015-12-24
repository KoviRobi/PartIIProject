import rmk35.partIIProject.backend.LambdaValue;
import rmk35.partIIProject.backend.RuntimeValue;
import rmk35.partIIProject.backend.NumberValue;

public class TestApplication
{ public static void main(String[] arguments)
  { LambdaValue lambda1 = new TestLambdaValue();
    LambdaValue lambda2 = new TestLambdaValue();
    System.out.println(lambda1.run(lambda2.run(new NumberValue(7))));
  }
}