package rmk35.partIIProject.backend.instructions.types;

public class ShortType implements JVMType
{ @Override
  public String prefix()
  { return "i"; // No specific short type
  }

  @Override
  public String toString()
  { return "S";
  }

  @Override
  public int stackCount()
  { return 1;
  }
}
