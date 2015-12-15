package rmk35.partIIProject.backend;

import java.util.List;
import java.util.ArrayList;

public interface OutputClass
{ /** Either the main() methiod for main class, or the run(args) method for the inner class */
  public void addToPrimaryMethod(String value);
  /** Generates a unique ID that does not start with a number */
  public String uniqueID();
}
