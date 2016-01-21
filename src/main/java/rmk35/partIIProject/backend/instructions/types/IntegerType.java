package rmk35.partIIProject.backend.instructions.types;

public class IntegerType implements JVMType
{ @Override
  public String prefix()
  { return "i";
  }

  @Override
  public String toString()
  { return "I";
  }

  @Override
  public int stackCount()
  { return 1;
  }
}
