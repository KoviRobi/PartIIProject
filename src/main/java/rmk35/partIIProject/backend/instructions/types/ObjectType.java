package rmk35.partIIProject.backend.instructions.types;

public class ObjectType implements JVMType
{ String objectClass;

  public ObjectType(Class<?> objectClass)
  { this.objectClass = objectClass.getName().replace('.', '/');
  }

  @Override
  public String prefix()
  { return "a"; // No specific boolean type
  }

  @Override
  public String toString()
  { return "L" + objectClass + ";";
  }

  @Override
  public int stackCount()
  { return 1;
  }
}
