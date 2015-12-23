package rmk35.partIIProject.backend;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class OutputClass
{ int stackLimit, stackCount;
  int localLimit, localCount;

  public OutputClass(int stackCount, int localCount)
  { this.stackCount = this.stackLimit = stackCount;
    this.localCount = this.localLimit = localCount;
  }

  public void  incrementStackCount(int n)
  { stackCount += n;
    stackLimit = Math.max(stackLimit, stackCount);
  }
  public void decrementStackCount(int n)
  { stackCount -= n;
    if (stackCount<0) throw new InternalCompilerException("Simulated stack underflown");
  }
  public void incrementLocalCount(int n)
  { localCount += n;
    localLimit = Math.max(localLimit, localCount);
  }
  public void decrementLocalCount(int n)
  { localCount -= n;
    if (localCount<0) throw new InternalCompilerException("Simulated locals underflown");
  }

  /** Either the main() methiod for main class, or the run(args) method for the inner class */
  public abstract void addToPrimaryMethod(String value);
  /** Generates a unique ID that does not start with a number */
  public abstract String uniqueID();
  abstract String getOutputFileName();
  abstract String getAssembly();
  abstract MainClass getMainClass();

  public void saveToDisk() throws IOException
  { try (BufferedWriter writer =
            new BufferedWriter
              (new FileWriter(getOutputFileName())))
    { writer.append(this.getAssembly());
    }
  }
  
}
