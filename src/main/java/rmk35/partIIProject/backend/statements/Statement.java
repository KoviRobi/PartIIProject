package rmk35.partIIProject.backend.statements;

import java.util.Collection;
import java.util.Map;
import rmk35.partIIProject.backend.OutputClass;

import lombok.ToString;

@ToString
public abstract class Statement
{ public abstract void generateOutput(OutputClass output);
  // Gets the free variables that need to be copied to the closure
  // Note, this excludes the globals, as they are not copied into the closure
  // And also the locals, as they are already copied at application time
  public abstract Collection<String> getFreeIdentifiers();
}
