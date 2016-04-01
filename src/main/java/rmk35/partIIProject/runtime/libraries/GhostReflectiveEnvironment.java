package rmk35.partIIProject.runtime.libraries;

import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;

public class GhostReflectiveEnvironment extends EnvironmentValue
{ EnvironmentValue environment;

  public GhostReflectiveEnvironment(EnvironmentValue environment)
  { this.environment = environment;
  }

  @Override
  public void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { environment.generateByteCode(mainClass, outputClass, method);
  }
}
