package scheme;

import rmk35.partIIProject.middle.bindings.Binding;
import rmk35.partIIProject.middle.bindings.JavaCallBinding;
import rmk35.partIIProject.middle.bindings.JavaClassBinding;
import rmk35.partIIProject.middle.bindings.JavaFieldBinding;
import rmk35.partIIProject.middle.bindings.JavaMethodBinding;
import rmk35.partIIProject.middle.bindings.JavaStaticFieldBinding;

public class java extends ReflectiveEnvironment
{ public java() { bind(); }
  public Binding java = new JavaCallBinding();
  public Binding clasX000073 = new JavaClassBinding();
  public Binding field = new JavaFieldBinding();
  public Binding method = new JavaMethodBinding();
  public Binding staticX00002Dfield = new JavaStaticFieldBinding();
}
