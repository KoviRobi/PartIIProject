import java.lang.reflect.*;

public class main
{ public static void main(String[] arguments) throws Exception
  { Class<?> systemClass = Class.forName("java.lang.System");
     System.out.println(systemClass);
     Class<?> out = systemClass.getField("out").get(systemClass).getClass();
     System.out.println(out);
     Method println = out.getMethod("println", Class.forName("java.lang.Object"));
     System.out.println(println);
  }
}