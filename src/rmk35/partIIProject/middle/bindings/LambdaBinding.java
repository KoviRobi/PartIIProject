package rmk35.partIIProject.middle.bindings;

import lombok.Value;

@Value
public class LambdaBinding implements Binding
{ List<String> formals;
  Statement body;
}
