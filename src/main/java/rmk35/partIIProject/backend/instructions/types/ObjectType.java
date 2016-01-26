package rmk35.partIIProject.backend.instructions.types;

import rmk35.partIIProject.InternalCompilerException;

public class ObjectType implements JVMType
{ String objectClass;

  public ObjectType()
  { this.objectClass = null;
  }
  public ObjectType(Class<?> objectClass)
  { this.objectClass = objectClass.getName().replace('.', '/');
  }

  @Override
  public String prefix()
  { return "a"; // Reference type
  }

  @Override
  public String toString()
  { if (objectClass != null)
    { return "L" + objectClass + ";";
    } else
    { throw new InternalCompilerException("Tried to access objectType's objectClass field, while it is null");
    }
  }

  @Override
  public int stackCount()
  { return 1;
  }
}
