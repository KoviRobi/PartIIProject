package rmk35.partIIProject.backend.instructions.types;

public class ArrayType implements JVMType
{ JVMType elementType;

  public ArrayType(JVMType elementType)
  { this.elementType = elementType;
  }

  @Override
  public String prefix()
  { return "a"; // Reference type
  }

  @Override
  public String toString()
  { return "[" + elementType.toString();
  }

  @Override
  public int stackCount()
  { return 1;
  }
}
