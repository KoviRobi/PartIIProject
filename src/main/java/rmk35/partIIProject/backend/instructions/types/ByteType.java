package rmk35.partIIProject.backend.instructions.types;

public class ByteType implements JVMType
{ @Override
  public String prefix()
  { return "i"; // No specific byte type
  }

  @Override
  public String toString()
  { return "B";
  }

  @Override
  public int stackCount()
  { return 1;
  }
}
