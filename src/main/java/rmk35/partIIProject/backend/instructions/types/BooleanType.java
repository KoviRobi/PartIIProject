package rmk35.partIIProject.backend.instructions.types;

public class BooleanType implements JVMType
{ @Override
  public String prefix()
  { return "i"; // No specific boolean type
  }

  @Override
  public String toString()
  { return "Z";
  }

  @Override
  public int stackCount()
  { return 1;
  }
}
