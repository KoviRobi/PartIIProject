package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;

public class TableSwitchInstruction implements Instruction
{ String defaultLabel;
  int low;
  String[] labels;

  public TableSwitchInstruction(String defaultLabel, int low, String... labels)
  { this.defaultLabel = defaultLabel;
    this.low = low;
    this.labels = labels;
  }

  public TableSwitchInstruction(String defaultLabel, int low, String prefix, int high)
  { this.defaultLabel = defaultLabel;
    this.low = low;
    this.labels =  new String[high-low+1];
    for (int i = 0; i < labels.length; i++)
    { this.labels[i] = prefix + (low + i);
    }
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  { method.decrementStackCount(1);
  }

  public String byteCode()
  { return "  tableswitch " + low + "\n" +
      "    " + String.join("\n    ", labels) + "\n" +
      "  default : " + defaultLabel;
  }
}