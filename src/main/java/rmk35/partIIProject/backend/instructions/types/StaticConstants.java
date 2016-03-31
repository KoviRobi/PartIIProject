package rmk35.partIIProject.backend.instructions.types;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.LambdaValue;
import rmk35.partIIProject.runtime.ObjectValue;

public class StaticConstants
{ public static final JVMType voidType = new VoidType();
  public static final JVMType booleanType = new BooleanType();
  public static final JVMType integerType = new IntegerType();
  public static final JVMType characterType = new CharacterType();
  public static final JVMType byteType = new ByteType();
  public static final JVMType byteArrayType = new ArrayType(new ByteType());
  public static final JVMType runtimeValueType = new ObjectType(RuntimeValue.class);
  public static final JVMType runtimeValueArrayType = new ArrayType(new ObjectType(RuntimeValue.class));
  public static final JVMType lambdaValueType = new ObjectType(LambdaValue.class);
  public static final JVMType objectValueType = new ObjectType(ObjectValue.class);
  public static final JVMType objectType = new ObjectType(Object.class);
  public static final JVMType objectArrayType = new ArrayType(new ObjectType(Object.class));
  public static final JVMType stringType = new ObjectType(String.class);
  public static final JVMType stringArrayType = new ArrayType(new ObjectType(String.class));
}
