package rmk35.partIIProject.backend.instructions.types;

import lombok.Value;

@Value
public class LongType implements JVMType
{ @Override
  public String prefix()
  { return "l";
  }

  @Override
  public String toString()
  { return "L";
  }

  @Override
  public int stackCount()
  { return 1;
  }
}
