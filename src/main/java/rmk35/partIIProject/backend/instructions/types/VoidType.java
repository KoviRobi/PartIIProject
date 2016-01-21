package rmk35.partIIProject.backend.instructions.types;

import rmk35.partIIProject.InternalCompilerException;

public class VoidType implements JVMType
{ @Override
  public String prefix()
  { throw new InternalCompilerException("Void does not have a prefix type");
  }

  @Override
  public String toString()
  { return "V";
  }

  @Override
  public int stackCount()
  { return 0;
  }
}
