package rmk35.partIIProject.backend.instructions.types;

public class CharacterType implements JVMType
{ @Override
  public String prefix()
  { return "i"; // No specific char type
  }

  @Override
  public String toString()
  { return "C";
  }

  @Override
  public int stackCount()
  { return 1;
  }
}
